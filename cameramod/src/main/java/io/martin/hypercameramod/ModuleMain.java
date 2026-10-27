package io.martin.hypercameramod;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraCharacteristics;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipFile;

import io.github.libxposed.api.XposedModule;

// Turns on three things the camera already contains but leaves off for this phone:
//  1. the Palette and Parameters filter panel (it needs the system property
//     camera.feature.styleVersion to be 2 or more, which the camera reads through
//     android.os.SystemProperties),
//  2. the "Original" colour style next to Leica Authentic and Leica Vibrant (bit 3 of the camera
//     characteristic xiaomi.capabilities.stylizationTypeMask, which this phone's camera hardware
//     layer does not set),
//  3. the Leica Essential CMOS (M10R) style, whose list entry is compiled out of this build.
// Each part is independent; one that does not fit the camera build is skipped and logged.
public class ModuleMain extends XposedModule {
    private static final String TAG = "HyperCameraMod";
    private static final String CAMERA_PACKAGE = "com.android.camera";

    // 1. Palette and Parameters
    private static final String STYLE_VERSION_KEY = "camera.feature.styleVersion";
    private static final int STYLE_VERSION = 2;

    // 2. Original colour style
    private static final String STYLIZATION_KEY = "xiaomi.capabilities.stylizationTypeMask";
    private static final int STYLIZATION_ORIGINAL = 1 << 3;

    // 3. M10R. The Leica Essential style list builds M3 (B&W) and M9 (CCD); a profile names where
    // that list and its item class live in one camera layout.
    private record LegendProfile(String name, String listClass, String itemClass,
                                 String stringIds, String imageIds) {
    }

    private static final LegendProfile[] LEGEND_PROFILES = {
            // Camera 6.7 (HyperOS 4): 6.7.000280.0 (tested), 6.7.000070.0 (has the item already).
            new LegendProfile("6.7", "s2.A", "com.android.camera.data.data.d", "ci.e", "ci.b"),
    };
    // The photography style picker (Leica Authentic, Leica Vibrant, and Original once its bit is
    // set). The Leica UI draws each as a badge from a mapping that knows only two values, so
    // Original comes out as the Authentic badge; the module swaps in its own "ORIG" badge.
    private record StyleProfile(String name, String listClass, String itemClass) {
    }

    private static final StyleProfile[] STYLE_PROFILES = {
            new StyleProfile("6.7", "s2.m", "com.android.camera.data.data.d"),
    };
    private static final String STYLE_ORIGINAL = "2";
    private static final String STYLE_AUTHENTIC = "1";
    private static final int ASSET_ORIGINAL_BADGE = 3;
    private static volatile android.content.pm.ApplicationInfo moduleInfo;

    private static final String M10R = "M10R";
    // The item's three images are not in builds that drop the item, so the module carries them: a
    // made-up resource id (this base plus an index into M10R_ASSETS) that ImageView.setImageResource
    // and Resources.getDrawable turn into the file from the module's assets.
    private static final int ASSET_ICON_BASE = 0x7e010000;
    private static final int ASSET_TEXT_BASE = 0x7e020000;   // the CMOS title string
    private static final String[] M10R_ASSETS = {"template", "icon", "text_icon"};
    private static final Map<Integer, Bitmap> ASSET_CACHE = new HashMap<>();
    private static volatile String moduleApk;

    // What did not work, shown to the user once per camera version.
    private static final String MSG_LEGEND =
            "The Leica Essential style list is different on this camera version, so the CMOS "
                    + "style was not added.";
    private final List<String> problems = new CopyOnWriteArrayList<>();
    private final AtomicBoolean noticeScheduled = new AtomicBoolean();

    private volatile boolean propertiesHooked;
    private volatile boolean stylizationHooked;
    private volatile boolean legendHooked;
    private volatile boolean assetsHooked;
    private volatile boolean styleHooked;
    private volatile boolean textHooked;

    @Override
    public void onPackageLoaded(PackageLoadedParam param) {
        install("onPackageLoaded", param.getPackageName(), param.getDefaultClassLoader());
    }

    @Override
    public void onPackageReady(PackageReadyParam param) {
        install("onPackageReady", param.getPackageName(), param.getClassLoader());
        if (CAMERA_PACKAGE.equals(param.getPackageName())) {
            scheduleNotice();
        }
    }

    private void install(String stage, String pkg, ClassLoader loader) {
        if (!CAMERA_PACKAGE.equals(pkg) || loader == null) {
            return;
        }
        try {
            moduleInfo = getModuleApplicationInfo();
            moduleApk = moduleInfo.sourceDir;
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot find the module APK: " + t);
        }
        installPropertyHook();
        installStylizationHook();
        installLegendHook(loader);
        installStyleHook(loader);
        if (!problems.isEmpty() && stage.equals("onPackageLoaded")) {
            // onPackageReady tries once more; report only what is still wrong after it
            problems.clear();
        }
    }

    // ---- 1. Palette and Parameters ----------------------------------------------------------

    private void installPropertyHook() {
        if (propertiesHooked) {
            return;
        }
        try {
            Class<?> props = Class.forName("android.os.SystemProperties");
            hook(props.getDeclaredMethod("getInt", String.class, int.class)).intercept(chain -> {
                Object result = chain.proceed();
                if (STYLE_VERSION_KEY.equals(chain.getArg(0)) && (Integer) result < STYLE_VERSION) {
                    return STYLE_VERSION;
                }
                return result;
            });
            hook(props.getDeclaredMethod("get", String.class, String.class)).intercept(chain -> {
                Object result = chain.proceed();
                if (STYLE_VERSION_KEY.equals(chain.getArg(0)) && parse(result) < STYLE_VERSION) {
                    return String.valueOf(STYLE_VERSION);
                }
                return result;
            });
            hook(props.getDeclaredMethod("get", String.class)).intercept(chain -> {
                Object result = chain.proceed();
                if (STYLE_VERSION_KEY.equals(chain.getArg(0)) && parse(result) < STYLE_VERSION) {
                    return String.valueOf(STYLE_VERSION);
                }
                return result;
            });
            propertiesHooked = true;
            log(Log.INFO, TAG, "hooked SystemProperties (" + STYLE_VERSION_KEY + " >= "
                    + STYLE_VERSION + ")");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook SystemProperties: " + t);
        }
    }

    private static int parse(Object value) {
        try {
            return value == null ? 0 : Integer.parseInt(value.toString().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ---- 2. Original colour style -------------------------------------------------------------

    private void installStylizationHook() {
        if (stylizationHooked) {
            return;
        }
        try {
            Method method = CameraCharacteristics.class.getDeclaredMethod("get",
                    CameraCharacteristics.Key.class);
            hook(method).intercept(chain -> {
                Object result = chain.proceed();
                if (result instanceof Integer mask
                        && chain.getArg(0) instanceof CameraCharacteristics.Key<?> key
                        && STYLIZATION_KEY.equals(key.getName())
                        && (mask & STYLIZATION_ORIGINAL) == 0) {
                    return mask | STYLIZATION_ORIGINAL;
                }
                return result;
            });
            stylizationHooked = true;
            log(Log.INFO, TAG, "hooked CameraCharacteristics.get (" + STYLIZATION_KEY + " bit 3)");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook CameraCharacteristics.get: " + t);
        }
    }

    // ---- 3. Leica Essential CMOS (M10R) ---------------------------------------------------------

    private void installLegendHook(ClassLoader loader) {
        if (legendHooked) {
            return;
        }
        for (LegendProfile profile : LEGEND_PROFILES) {
            try {
                Class<?> list = Class.forName(profile.listClass(), false, loader);
                Method getItems = list.getDeclaredMethod("getItems");
                Class<?> item = Class.forName(profile.itemClass(), false, loader);
                allocate(item);   // fail now, not in the hook, if instances cannot be made
                Field key = field(item, "r");
                Field title = field(item, "l");
                Field alias = field(item, "m");
                Field intro = field(item, "n");
                Field image = field(item, "g");
                Field template = field(item, "h");
                Field icon = field(item, "i");
                Field textIcon = field(item, "c");
                Class<?> strings = Class.forName(profile.stringIds(), false, loader);
                Class<?> images = Class.forName(profile.imageIds(), false, loader);

                hook(getItems).intercept(chain -> {
                    Object result = chain.proceed();
                    try {
                        if (result instanceof List<?> raw) {
                            @SuppressWarnings("unchecked")
                            List<Object> items = (List<Object>) raw;
                            Object ccd = null;
                            for (Object entry : items) {
                                if (M10R.equals(key.get(entry))) {
                                    return result;
                                }
                                if ("M9".equals(key.get(entry))) {
                                    ccd = entry;
                                }
                            }
                            if (ccd == null) {
                                return result;
                            }
                            // Start from the CCD (M9) item, so every field the camera's theme
                            // fills in is there, then swap in the CMOS equivalents.
                            Object entry = allocate(item);
                            for (Field f : item.getDeclaredFields()) {
                                if (!java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                                    f.setAccessible(true);
                                    f.set(entry, f.get(ccd));
                                }
                            }
                            key.set(entry, M10R);
                            // The CMOS title string is not in this build's resources: the
                            // module supplies it (see installTextHook).
                            title.setInt(entry, ASSET_TEXT_BASE);
                            alias.setInt(entry, swap(strings, alias.getInt(ccd), "legend_ccd_alias",
                                    "legend_cmos_alias"));
                            intro.setInt(entry, swap(strings, intro.getInt(ccd), "m9", "m10r"));
                            image.setInt(entry, swap(images, image.getInt(ccd), "m9", "m10r"));
                            template.setInt(entry, ASSET_ICON_BASE);
                            icon.setInt(entry, ASSET_ICON_BASE | 1);
                            textIcon.setInt(entry, ASSET_ICON_BASE | 2);
                            items.add(Math.min(1, items.size()), entry);   // after M3, before M9
                            log(Log.INFO, TAG, "added the M10R (CMOS) style to the Leica Essential list");
                        }
                    } catch (Throwable t) {
                        log(Log.ERROR, TAG, "could not add the M10R style", t);
                    }
                    return result;
                });
                legendHooked = true;
                installAssetHooks();
                installTextHook();
                log(Log.INFO, TAG, "hooked " + profile.listClass() + ".getItems (Leica Essential, "
                        + "layout " + profile.name() + ")");
                return;
            } catch (Throwable t) {
                log(Log.WARN, TAG, "Leica Essential layout " + profile.name()
                        + " does not match: " + t);
            }
        }
        log(Log.WARN, TAG, "the Leica Essential style list is not one this module knows; "
                + "the CMOS style is not added");
        if (!problems.contains(MSG_LEGEND)) {
            problems.add(MSG_LEGEND);
        }
    }

    // The id of the camera resource that matches the CCD one in the same id class (by the name the
    // id class gives it): the name with "from" replaced by "to", or the id itself when there is no
    // such resource.
    private static int swap(Class<?> ids, int id, String from, String to) {
        try {
            for (Field f : ids.getFields()) {
                if (f.getType() == int.class && f.getInt(null) == id) {
                    String name = f.getName().contains(from) && !f.getName().equals(from)
                            ? f.getName().replace(from, to)
                            : f.getName().equals(from) ? to : f.getName();
                    return ids.getField(name).getInt(null);
                }
            }
        } catch (Throwable ignored) {
        }
        return id;
    }

    // Texts the camera build does not have: a made-up string id (this base) answered from the
    // module's own strings, in the phone's language.
    private void installTextHook() {
        if (textHooked) {
            return;
        }
        try {
            Method getText = Resources.class.getDeclaredMethod("getText", int.class);
            hook(getText).intercept(chain -> {
                int id = (Integer) chain.getArg(0);
                if ((id & 0xFFFF0000) != ASSET_TEXT_BASE) {
                    return chain.proceed();
                }
                try {
                    Application app = currentApplication();
                    Resources own = app.getPackageManager().getResourcesForApplication(moduleInfo);
                    return own.getText(R.string.m10r_title);
                } catch (Throwable t) {
                    return "CMOS";
                }
            });
            textHooked = true;
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook Resources.getText: " + t);
        }
    }

    // The camera's item class has no constructor in this build (the build inlined the empty one
    // away), so an instance is made without calling one; the fields are all set by the caller.
    private static Object allocate(Class<?> cls) throws ReflectiveOperationException {
        Class<?> unsafe = Class.forName("sun.misc.Unsafe");
        Field the = unsafe.getDeclaredField("theUnsafe");
        the.setAccessible(true);
        return unsafe.getMethod("allocateInstance", Class.class).invoke(the.get(null), cls);
    }

    private static Field field(Class<?> cls, String name) throws NoSuchFieldException {
        Field field = cls.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private void installStyleHook(ClassLoader loader) {
        if (styleHooked) {
            return;
        }
        for (StyleProfile profile : STYLE_PROFILES) {
            try {
                Class<?> list = Class.forName(profile.listClass(), false, loader);
                Method getItems = list.getDeclaredMethod("getItems");
                Class<?> item = Class.forName(profile.itemClass(), false, loader);
                Field key = field(item, "r");
                Field[] images = {field(item, "c"), field(item, "g"), field(item, "h")};

                hook(getItems).intercept(chain -> {
                    Object result = chain.proceed();
                    try {
                        if (result instanceof List<?> items) {
                            Object original = null;
                            Object authentic = null;
                            for (Object entry : items) {
                                if (STYLE_ORIGINAL.equals(key.get(entry))) {
                                    original = entry;
                                } else if (STYLE_AUTHENTIC.equals(key.get(entry))) {
                                    authentic = entry;
                                }
                            }
                            // Only when Original really shares the Authentic Leica UI badge
                            if (original != null && authentic != null
                                    && images[0].getInt(original) == images[0].getInt(authentic)
                                    && isLeicaBadge(images[0].getInt(original))) {
                                for (Field f : images) {
                                    f.setInt(original, ASSET_ICON_BASE | ASSET_ORIGINAL_BADGE);
                                }
                                log(Log.INFO, TAG, "gave the Original colour style its own Leica UI badge");
                            }
                        }
                    } catch (Throwable t) {
                        log(Log.ERROR, TAG, "could not fix the Original colour style badge", t);
                    }
                    return result;
                });
                styleHooked = true;
                installAssetHooks();
                log(Log.INFO, TAG, "hooked " + profile.listClass() + ".getItems (colour styles, "
                        + "layout " + profile.name() + ")");
                return;
            } catch (Throwable t) {
                log(Log.WARN, TAG, "colour style layout " + profile.name()
                        + " does not match: " + t);
            }
        }
    }

    // The Leica UI's badges are the drawables whose names end in _lc_sp
    private static boolean isLeicaBadge(int id) {
        try {
            Application app = currentApplication();
            return app != null && app.getResources().getResourceEntryName(id).endsWith("_lc_sp");
        } catch (Throwable t) {
            return false;
        }
    }

    // The item's images are made-up resource ids, answered here wherever the camera loads them:
    // ImageView.setImageResource (the style picker) and Resources.getDrawable.
    private void installAssetHooks() {
        if (assetsHooked) {
            return;
        }
        try {
            Method getDrawable = Resources.class.getDeclaredMethod("getDrawable", int.class,
                    Resources.Theme.class);
            hook(getDrawable).intercept(chain -> {
                int id = (Integer) chain.getArg(0);
                if ((id & 0xFFFF0000) != ASSET_ICON_BASE) {
                    return chain.proceed();
                }
                return assetDrawable((Resources) chain.getThisObject(), id & 0xFFFF);
            });
            Method setImage = ImageView.class.getDeclaredMethod("setImageResource", int.class);
            hook(setImage).intercept(chain -> {
                int id = (Integer) chain.getArg(0);
                if ((id & 0xFFFF0000) != ASSET_ICON_BASE) {
                    return chain.proceed();
                }
                ImageView view = (ImageView) chain.getThisObject();
                view.setImageDrawable(assetDrawable(view.getResources(), id & 0xFFFF));
                return null;
            });
            assetsHooked = true;
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook the image loading for the M10R images: " + t);
        }
    }

    // One of the M10R images, read from the module's own APK.
    private static synchronized Drawable assetDrawable(Resources resources, int index) {
        if (index == ASSET_ORIGINAL_BADGE) {
            try {
                Application app = currentApplication();
                Resources own = app.getPackageManager().getResourcesForApplication(moduleInfo);
                return own.getDrawable(R.drawable.ic_cvtype_original_lc_sp, null);
            } catch (Throwable t) {
                Log.w(TAG, "cannot load the Original badge: " + t);
                return new ColorDrawable(Color.TRANSPARENT);
            }
        }
        Bitmap bitmap = ASSET_CACHE.get(index);
        if (bitmap == null) {
            try (ZipFile zip = new ZipFile(moduleApk)) {
                var entry = zip.getEntry("assets/m10r/" + M10R_ASSETS[index] + ".webp");
                try (var in = zip.getInputStream(entry)) {
                    bitmap = BitmapFactory.decodeStream(in);
                }
                ASSET_CACHE.put(index, bitmap);
            } catch (Throwable t) {
                Log.w(TAG, "cannot read the M10R image " + index + ": " + t);
                return new ColorDrawable(Color.TRANSPARENT);
            }
        }
        return new BitmapDrawable(resources, bitmap);
    }

    // ---- telling the user ---------------------------------------------------------------------
    // A module has no screen inside the camera, so a problem is shown as a toast the first time
    // the camera starts after a camera update, then not again for that camera version.

    private void scheduleNotice() {
        if (problems.isEmpty() || !noticeScheduled.compareAndSet(false, true)) {
            return;
        }
        var handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            private int attempts;

            @Override
            public void run() {
                try {
                    Application app = currentApplication();
                    if (app == null) {
                        if (++attempts < 8) {
                            handler.postDelayed(this, 1500);
                        }
                        return;
                    }
                    showNotice(app);
                } catch (Throwable t) {
                    log(Log.WARN, TAG, "could not show the notice: " + t);
                }
            }
        }, 3000);
    }

    private void showNotice(Application app) throws Exception {
        String version = app.getPackageManager().getPackageInfo(app.getPackageName(), 0)
                .versionName;
        StringBuilder text = new StringBuilder();
        for (String problem : problems) {
            text.append(text.length() == 0 ? "" : " ").append(problem);
        }
        String key = "notice_" + version + "_" + text.toString().hashCode();
        var prefs = app.getSharedPreferences(TAG, Application.MODE_PRIVATE);
        if (prefs.getBoolean(key, false)) {
            return;
        }
        prefs.edit().putBoolean(key, true).apply();
        Toast.makeText(app, TAG + " (camera " + version + "): " + text, Toast.LENGTH_LONG).show();
    }

    private static Application currentApplication() {
        try {
            return (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null);
        } catch (Throwable t) {
            return null;
        }
    }
}
