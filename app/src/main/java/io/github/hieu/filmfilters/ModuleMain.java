package io.github.hieu.filmfilters;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    // ic_vector_lc_looks_filter_name_icon_standard in 6.7.000280.0 (no R classes at runtime).
    private static final int LOOKS_NAME_ICON = 0x7f0808f5;

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

    // The Leica Looks list names the filters by their film codes, and does not list KP160.
    private static final Film[] LOOKS_FILMS = {
            new Film(81, "R600", "color_effect_entry_r_600", "color_effect_image_r_600", 14, 56),
            new Film(82, "P100F", "color_effect_entry_p_100f", "color_effect_image_p_100f", 15, 57),
            new Film(83, "F-50", "color_effect_entry_f_50", "color_effect_image_f_50", 16, 46),
            new Film(84, "KC64", "color_effect_entry_kc_64", "color_effect_image_c_64", 17, 38),
            new Film(87, "KP160", "color_effect_entry_kp_160", "color_effect_image_p_160nc", 18, 58),
    };

    // The Looks list draws a small image of each look's name under its thumbnail. There is no
    // such image for the film filters, so the name icon is a made-up resource id (this base plus
    // the filter ordinal) that ImageView.setImageResource turns into the film's name as text.
    private static final int NAME_ICON_BASE = 0x7e000000;
    // Same coordinate system as the camera's own name icons (36 dp square), so the label is drawn
    // at the same size wherever the icon is used. Letter height and stroke are those of the
    // native "LEICA" line as measured on screen (26 px tall, 4 px stroke, letters 3-4 px apart,
    // 5 px between the two lines, at about 2.65 px per dp).
    private static final float LABEL_SIZE_DP = 36f;
    private static final float LABEL_CAP_DP = 9.45f;
    private static final float LABEL_GAP_DP = 1.05f;    // gap between letters, close to native
    private static final float LABEL_LINE_GAP_DP = 2.25f; // gap between the two lines (31 px pitch at 2.65 px/dp), as native
    private static final float LABEL_BLOCK_SHIFT_DP = -0.15f; // moves both lines up, as native
    private static final String LABEL_TOP_LINE = "FLM";
    private static final float HYPHEN_LENGTH = 0.5f;    // hyphen length, as a share of letter height
    private static final Map<Character, Bitmap> GLYPH_CACHE = new HashMap<>();

    private volatile boolean hooked;
    private volatile boolean nameIconHooked;

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
                addFilms(result, FILMS, create, ordinalField, cloudField, strings, images);
                return result;
            });
            hooked = true;
            log(Log.INFO, TAG, stage + ": hooked " + LIST_CLASS + "." + LIST_METHOD);
            installPanelHook(loader);
            installNameIconHook();
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

    // The name icon of a film item is a made-up resource id, so every way the camera loads it
    // has to be answered here: Resources.getDrawable (Context.getDrawable and ImageView end up
    // there) and, as a shortcut, ImageView.setImageResource. Without the first, the camera
    // throws Resources.NotFoundException when it shows the filter's name in its top banner.
    private void installNameIconHook() {
        try {
            var method = Resources.class.getDeclaredMethod("getDrawable", int.class,
                    Resources.Theme.class);
            hook(method).intercept(chain -> {
                int id = (Integer) chain.getArg(0);
                if ((id & 0xFFFF0000) != NAME_ICON_BASE) {
                    return chain.proceed();
                }
                return nameDrawable((Resources) chain.getThisObject(), id & 0xFFFF);
            });
            nameIconHooked = true;
            log(Log.INFO, TAG, "hooked Resources.getDrawable (film name labels)");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook Resources.getDrawable, using the stock name icon: "
                    + t);
            return;
        }
        try {
            var method = ImageView.class.getDeclaredMethod("setImageResource", int.class);
            hook(method).intercept(chain -> {
                int id = (Integer) chain.getArg(0);
                if ((id & 0xFFFF0000) != NAME_ICON_BASE) {
                    return chain.proceed();
                }
                ImageView view = (ImageView) chain.getThisObject();
                view.setImageDrawable(nameDrawable(view.getResources(), id & 0xFFFF));
                return null;
            });
            log(Log.INFO, TAG, "hooked ImageView.setImageResource (film name labels)");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook ImageView.setImageResource: " + t);
        }
    }

    // The label image. It extends VectorDrawable because the camera only tints vector drawables
    // (the top banner tints its name icon), so it has to be one for the label to be tinted too.
    private static final class LabelDrawable extends VectorDrawable {
        private final Bitmap bitmap;
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        private ColorStateList tint;
        private PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;

        LabelDrawable(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(bitmap, null, getBounds(), paint);
        }

        @Override
        public int getIntrinsicWidth() {
            return bitmap.getWidth();
        }

        @Override
        public int getIntrinsicHeight() {
            return bitmap.getHeight();
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
            invalidateSelf();
        }

        @Override
        public int getAlpha() {
            return paint.getAlpha();
        }

        @Override
        public void setColorFilter(ColorFilter filter) {
            paint.setColorFilter(filter);
            invalidateSelf();
        }

        @Override
        public ColorFilter getColorFilter() {
            return paint.getColorFilter();
        }

        @Override
        public void setTintList(ColorStateList tint) {
            this.tint = tint;
            applyTint();
        }

        @Override
        public void setTintMode(PorterDuff.Mode mode) {
            tintMode = mode;
            applyTint();
        }

        private void applyTint() {
            paint.setColorFilter(tint == null ? null
                    : new PorterDuffColorFilter(tint.getDefaultColor(), tintMode));
            invalidateSelf();
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }

    // The film label in the Leica label typeface, white on a transparent square the size of the
    // name icon; the panel tints its name images. Like the camera's own two-line labels, it has
    // a short top word ("FLM", for film) above the film's name, both lines centred as a block.
    // Every label uses the same letter height, weight and spacing, and natural widths. Letter
    // height and line positions are whole pixels, so the top and bottom edges of both lines are
    // equally sharp (a fractional position blurs one edge and makes the line look shorter).
    private static Drawable nameDrawable(Resources resources, int ordinal) {
        String label = "";
        for (Film film : LOOKS_FILMS) {
            if (film.ordinal() == ordinal) {
                label = film.label();
            }
        }
        float density = resources.getDisplayMetrics().density;
        int size = Math.round(LABEL_SIZE_DP * density);
        int letterHeight = Math.round(LABEL_CAP_DP * density);
        int pitch = Math.round((LABEL_CAP_DP + LABEL_LINE_GAP_DP) * density);   // line 1 top to line 2 top
        int top = Math.round((size - (letterHeight + pitch)) / 2f + LABEL_BLOCK_SHIFT_DP * density);
        float scale = letterHeight / (float) LeicaFont.CAP_HEIGHT;

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        // Report the current screen density so the drawable is exactly size x size pixels
        // (a vector of 36 dp is, whatever display-size setting is in use).
        bitmap.setDensity(resources.getDisplayMetrics().densityDpi);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.WHITE);
        drawLine(canvas, paint, LABEL_TOP_LINE, size, top, scale, density);
        drawLine(canvas, paint, label, size, top + pitch, scale, density);
        return new LabelDrawable(bitmap);
    }

    // One line of text, centred horizontally, with its top edge at y.
    private static void drawLine(Canvas canvas, Paint paint, String text, int width, float y,
                                 float scale, float density) {
        float cap = LeicaFont.CAP_HEIGHT;
        float gap = LABEL_GAP_DP * density;
        float hyphen = HYPHEN_LENGTH * cap * scale;

        float total = gap * (text.length() - 1);
        for (char c : text.toCharArray()) {
            Bitmap glyph = c == '-' ? null : glyph(c);
            total += c == '-' ? hyphen : glyph == null ? 0 : glyph.getWidth() * scale;
        }
        float x = (width - total) / 2f;
        for (char c : text.toCharArray()) {
            if (c == '-') {
                float thick = LeicaFont.STROKE * scale;
                float mid = y + cap * scale / 2f;
                canvas.drawRoundRect(new RectF(x, mid - thick / 2f, x + hyphen, mid + thick / 2f),
                        thick / 2f, thick / 2f, paint);
                x += hyphen;
            } else {
                Bitmap glyph = glyph(c);
                if (glyph != null) {
                    float glyphWidth = glyph.getWidth() * scale;
                    canvas.drawBitmap(glyph, null,
                            new RectF(x, y, x + glyphWidth, y + cap * scale), paint);
                    x += glyphWidth;
                }
            }
            x += gap;
        }
    }

    private static synchronized Bitmap glyph(char c) {
        Bitmap glyph = GLYPH_CACHE.get(c);
        if (glyph == null) {
            for (String[] entry : LeicaFont.GLYPHS) {
                if (entry[0].charAt(0) == c) {
                    byte[] data = Base64.decode(entry[1], Base64.DEFAULT);
                    glyph = BitmapFactory.decodeByteArray(data, 0, data.length);
                    GLYPH_CACHE.put(c, glyph);
                }
            }
        }
        return glyph;
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
            int nameIcon = LOOKS_NAME_ICON;

            hook(method).intercept(chain -> {
                Object result = chain.proceed();
                try {
                    for (Object entry : addFilms(result, LOOKS_FILMS, create, ordinalField,
                            cloudField, strings, images)) {
                        int icon = iconField.getInt(entry);
                        nameIconField.setInt(entry, nameIconHooked
                                ? NAME_ICON_BASE | ordinalField.getInt(entry) : nameIcon);
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
    private List<Object> addFilms(Object result, Film[] films, Constructor<?> create,
                                  Field ordinalField, Field cloudField, Class<?> strings,
                                  Class<?> images) {
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
            for (Film film : films) {
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
