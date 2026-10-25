package io.github.hieu.filmfilters;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.github.libxposed.api.XposedModule;

public class ModuleMain extends XposedModule {
    private static final String TAG = "FilmFilters";
    private static final String CAMERA_PACKAGE = "com.android.camera";

    // Camera 6.7.000280.0 is obfuscated. Ii.i0.d() builds the photo filter list (category 10)
    // as a list of j3.b items; the items below were left out for this device.
    private static final String LIST_CLASS = "Ii.i0";
    private static final String LIST_METHOD = "d";
    private static final String ITEM_CLASS = "j3.b";
    private static final String STRING_IDS = "Di.p";
    private static final String IMAGE_IDS = "Di.n";
    private static final int PHOTO_CATEGORY = 10;
    // The filter panel (FragmentFilter). Its ut() says whether to merge the list with the cloud
    // filter list, which drops any local filter the cloud list does not name.
    private static final String PANEL_CLASS = "com.android.camera.fragment.m0";
    private static final String PANEL_METHOD = "ut";
    // Leica UI: E9.h.c() builds the "Looks" list (back camera) from a fixed table.
    private static final String LOOKS_CLASS = "E9.h";
    private static final String LOOKS_METHOD = "c";
    private static final String DRAWABLE_IDS = "com.android.camera.R$drawable";
    private static final String LOOKS_NAME_ICON = "ic_vector_lc_looks_filter_name_icon_standard";

    // ordinal = position of the filter in the camera's filter enum (p3.d), order = sort slot,
    // cloudId = the id the camera's own item builder gives the same filter.
    private record Film(int ordinal, String label, String stringId, String imageId, int order,
                        int cloudId) {
    }

    private static final Film[] FILMS = {
            new Film(81, "R600", "color_effect_entry_r_600", "color_effect_image_r_600", 14, 56),
            new Film(82, "P100F", "color_effect_entry_p_100f", "color_effect_image_p_100f", 15, 57),
            new Film(83, "F-50", "color_effect_entry_reversal", "color_effect_image_f_50", 16, 46),
            new Film(84, "KC64", "color_effect_entry_hanjiao", "color_effect_image_c_64", 17, 38),
    };

    private volatile boolean hooked;

    @Override
    public void onPackageLoaded(PackageLoadedParam param) {
        install("onPackageLoaded", param.getPackageName(), param.getDefaultClassLoader());
    }

    @Override
    public void onPackageReady(PackageReadyParam param) {
        install("onPackageReady", param.getPackageName(), param.getClassLoader());
    }

    private void install(String stage, String pkg, ClassLoader loader) {
        if (!CAMERA_PACKAGE.equals(pkg) || hooked || loader == null) {
            return;
        }
        try {
            var builder = Class.forName(LIST_CLASS, false, loader);
            var method = builder.getDeclaredMethod(LIST_METHOD);

            Class<?> item = Class.forName(ITEM_CLASS, false, loader);
            Constructor<?> create = item.getDeclaredConstructor(int.class, int.class, int.class,
                    int.class, int.class, int.class, String.class);
            create.setAccessible(true);
            Field ordinalField = item.getDeclaredField("b");
            ordinalField.setAccessible(true);
            Field cloudField = item.getDeclaredField("l");
            cloudField.setAccessible(true);
            Class<?> strings = Class.forName(STRING_IDS, false, loader);
            Class<?> images = Class.forName(IMAGE_IDS, false, loader);

            hook(method).intercept(chain -> {
                Object result = chain.proceed();
                addFilms(result, create, ordinalField, cloudField, strings, images);
                return result;
            });
            hooked = true;
            log(Log.INFO, TAG, stage + ": hooked " + LIST_CLASS + "." + LIST_METHOD);
            installPanelHook(loader);
            installLooksHook(loader, item, create, ordinalField, cloudField, strings, images);
            installDiagnostics(loader, ordinalField);
        } catch (Throwable t) {
            log(Log.WARN, TAG, stage + ": cannot hook " + LIST_CLASS + "." + LIST_METHOD
                    + " (unsupported camera build?): " + t);
        }
    }

    private void installPanelHook(ClassLoader loader) {
        try {
            var panel = Class.forName(PANEL_CLASS, false, loader);
            var method = panel.getDeclaredMethod(PANEL_METHOD);
            if (method.getReturnType() != boolean.class) {
                log(Log.WARN, TAG, PANEL_CLASS + "." + PANEL_METHOD + " does not return boolean");
                return;
            }
            hook(method).intercept(chain -> {
                log(Log.INFO, TAG, PANEL_METHOD + " called, returning false (no cloud merge)");
                return Boolean.FALSE;
            });
            log(Log.INFO, TAG, "hooked " + PANEL_CLASS + "." + PANEL_METHOD);
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook " + PANEL_CLASS + "." + PANEL_METHOD + ": " + t);
        }
    }

    // The Leica UI lists "Looks" from a separate fixed table (E9.h.c). Add the same film items
    // there, filling in the extra fields that panel reads (name icon, sample images).
    private void installLooksHook(ClassLoader loader, Class<?> item, Constructor<?> create,
                                  Field ordinalField, Field cloudField, Class<?> strings,
                                  Class<?> images) {
        try {
            var looks = Class.forName(LOOKS_CLASS, false, loader);
            var method = looks.getDeclaredMethod(LOOKS_METHOD);
            Field iconField = item.getDeclaredField("e");
            iconField.setAccessible(true);
            Field nameIconField = item.getDeclaredField("d");
            nameIconField.setAccessible(true);
            Field samplesField = item.getDeclaredField("g");
            samplesField.setAccessible(true);
            Field labelsField = item.getDeclaredField("h");
            labelsField.setAccessible(true);
            Field descField = item.getDeclaredField("i");
            descField.setAccessible(true);
            int nameIcon = Class.forName(DRAWABLE_IDS, false, loader).getField(LOOKS_NAME_ICON)
                    .getInt(null);

            hook(method).intercept(chain -> {
                Object result = chain.proceed();
                try {
                    for (Object entry : addFilms(result, create, ordinalField, cloudField,
                            strings, images)) {
                        int icon = iconField.getInt(entry);
                        nameIconField.setInt(entry, nameIcon);
                        samplesField.set(entry, new ArrayList<>(List.of(icon, icon, icon)));
                        labelsField.set(entry, new ArrayList<>(List.of(icon, icon, icon)));
                        descField.setInt(entry, 0);
                    }
                } catch (Throwable t) {
                    log(Log.ERROR, TAG, "could not finish Looks items", t);
                }
                return result;
            });
            log(Log.INFO, TAG, "hooked " + LOOKS_CLASS + "." + LOOKS_METHOD + " (Leica Looks)");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook " + LOOKS_CLASS + "." + LOOKS_METHOD + ": " + t);
        }
    }

    // Log-only hooks that show how big the filter list is at each stage between the camera's
    // list builder and the panel, to find where the added items are dropped.
    private void installDiagnostics(ClassLoader loader, Field ordinalField) {
        watch(loader, "com.xiaomi.camera.effect.EffectController", "q", 1, "stored list",
                ordinalField);
        watch(loader, "s2.a", "n", 2, "restoreItems", ordinalField);
        watch(loader, LOOKS_CLASS, LOOKS_METHOD, 0, "looks list", ordinalField);
    }

    private void watch(ClassLoader loader, String className, String methodName, int params,
                       String label, Field ordinalField) {
        try {
            var cls = Class.forName(className, false, loader);
            for (var method : cls.getDeclaredMethods()) {
                if (!method.getName().equals(methodName) || method.getParameterCount() != params) {
                    continue;
                }
                hook(method).intercept(chain -> {
                    Object result = chain.proceed();
                    StringBuilder line = new StringBuilder(label).append(": args=");
                    for (Object arg : chain.getArgs()) {
                        if (arg instanceof List<?> in) {
                            line.append("list(").append(in.size()).append(") ")
                                    .append(ordinals(in, ordinalField)).append(' ');
                        } else {
                            line.append(arg).append(' ');
                        }
                    }
                    if (result instanceof List<?> out) {
                        line.append("-> list(").append(out.size()).append(") ")
                                .append(ordinals(out, ordinalField));
                    }
                    log(Log.INFO, TAG, line.toString());
                    return result;
                });
                log(Log.INFO, TAG, "watching " + className + "." + methodName);
            }
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot watch " + className + "." + methodName + ": " + t);
        }
    }

    private static String ordinals(List<?> list, Field ordinalField) {
        StringBuilder out = new StringBuilder("[");
        for (Object entry : list) {
            try {
                out.append(ordinalField.getInt(entry)).append(',');
            } catch (Throwable ignored) {
                out.append('?').append(',');
            }
        }
        return out.append(']').toString();
    }

    // Appends any missing film items to the finished list. Leaves the list alone on any surprise.
    @SuppressWarnings("unchecked")
    private List<Object> addFilms(Object result, Constructor<?> create, Field ordinalField,
                                  Field cloudField, Class<?> strings, Class<?> images) {
        List<Object> created = new ArrayList<>();
        try {
            if (!(result instanceof List<?>)) {
                return created;
            }
            List<Object> list = (List<Object>) result;
            Set<Integer> present = new HashSet<>();
            for (Object entry : list) {
                present.add(ordinalField.getInt(entry));
            }
            List<String> added = new ArrayList<>();
            for (Film film : FILMS) {
                if (present.contains(film.ordinal())) {
                    continue;
                }
                int name = strings.getField(film.stringId()).getInt(null);
                int image = images.getField(film.imageId()).getInt(null);
                Object entry = create.newInstance(PHOTO_CATEGORY, film.ordinal(), name, image, 0,
                        film.order(), "NORMAL");
                cloudField.setInt(entry, film.cloudId());
                list.add(entry);
                created.add(entry);
                added.add(film.label());
            }
            log(Log.INFO, TAG, "filter list: " + present.size() + " items, added " + added);
        } catch (Throwable t) {
            log(Log.ERROR, TAG, "could not add film filters", t);
        }
        return created;
    }
}
