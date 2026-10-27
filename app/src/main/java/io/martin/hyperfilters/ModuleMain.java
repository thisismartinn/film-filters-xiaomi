package io.martin.hyperfilters;

import android.app.Application;
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
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.libxposed.api.XposedModule;

public class ModuleMain extends XposedModule {
    private static final String TAG = "HyperFilmFilters";
    private static final String CAMERA_PACKAGE = "com.android.camera";
    private static final int PHOTO_CATEGORY = 10;

    // Where the camera keeps its filter code. The camera is obfuscated, so the names change from
    // build to build; a profile is one known layout. The module tries each profile in turn and
    // uses the first whose classes, methods and fields all exist. When none fits, the module
    // leaves the camera alone and tells the user.
    private record Profile(
            String name,
            // Builds the photo filter list (category 10) as a list of items.
            String listClass, String listMethod,
            // The filter item and its fields: filter ordinal, cloud id, icon, name icon,
            // sample images, sample labels, description.
            String itemClass, String ordinalField, String cloudField, String iconField,
            String nameIconField, String samplesField, String labelsField, String descField,
            // The camera's resource id classes (strings, images). Optional: when missing the
            // ids are looked up by name instead.
            String stringIds, String imageIds,
            // The filter panel. Its method says whether to merge the list with the cloud list,
            // which drops any local filter the cloud list does not name.
            String panelClass, String panelMethod,
            // Leica UI: builds the "Looks" list (back camera) from a fixed table.
            String looksClass, String looksMethod,
            // The filter strip's workspace: after the cloud data arrives it adds every filter the
            // strip does not have yet at position 1, one after the other. The strip's items keep
            // their filter's data object in itemDataField (the same objects as in the camera's
            // filter component, whose order is the order the camera lists its filters in);
            // sortIndexMethod gives an item its place in the strip, and initMethod sets the
            // workspace up when the camera opens. The component is found through the camera's data
            // registry.
            String workspaceClass, String itemDataField, String sortIndexMethod,
            String initMethod, String registryClass, String registryMethod, String lookupMethod,
            String componentClass) {
    }

    private static final Profile[] PROFILES = {
            // Camera 6.7.000280.0 (HyperOS 4.0.5.0).
            new Profile("6.7", "Ii.i0", "d",
                    "j3.b", "b", "l", "e", "d", "g", "h", "i",
                    "Di.p", "Di.n",
                    "com.android.camera.fragment.m0", "ut",
                    "E9.h", "c",
                    "com.android.camera.features.mode.capture.e", "L", "Y", "G",
                    "h2.a", "a", "x", "s2.t"),
    };

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
    private static final float LABEL_GAP_DP = 1.05f;    // gap between the letters of the top line, as native
    private static final float LABEL_NAME_GAP_DP = 1.25f; // same for the film code: its letters sat tighter than native
    private static final float LABEL_LINE_GAP_DP = 2.25f; // gap between the two lines (31 px pitch at 2.65 px/dp), as native
    private static final float LABEL_BLOCK_SHIFT_DP = -0.4f; // moves both lines up, as native (outer edges incl. the ring)
    private static final float LABEL_GLYPH_HEIGHT_DP = 9.1f; // letter height; the widths stay those of LABEL_CAP_DP
    // The native labels have a light gray outline around the letters (about 1.75 px at 2.65 px per
    // dp): white letters keep a gray rim on a bright background, and gray (unselected) letters get
    // that much heavier. The outline is not tinted, only the letters are.
    private static final float LABEL_RING_DP = 0.66f;
    // The outline is translucent black (22%), not a fixed gray: over a near-white panel it comes
    // out as the light gray of the native labels (#C1C1C1), and over a dark panel it is hardly
    // visible, as on the native labels.
    private static final int LABEL_RING_ALPHA = 56;
    private static final String LABEL_TOP_LINE = "FLM";
    private static final float HYPHEN_LENGTH = 0.5f;    // hyphen length, as a share of letter height
    private static final Map<Character, Bitmap> GLYPH_CACHE = new HashMap<>();

    private volatile boolean hooked;
    private volatile boolean nameIconHooked;
    // What did not work, shown to the user once per camera version (see scheduleNotice).
    private final List<String> problems = new CopyOnWriteArrayList<>();
    private final AtomicBoolean noticeScheduled = new AtomicBoolean();

    // What a profile resolved to on the running camera.
    private record Resolved(Profile profile, Constructor<?> create, Field ordinal, Field cloud,
                            Class<?> strings, Class<?> images) {
    }

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
        if (!CAMERA_PACKAGE.equals(pkg) || hooked || loader == null) {
            return;
        }
        problems.clear();
        for (Profile profile : PROFILES) {
            Resolved resolved;
            try {
                resolved = resolve(profile, loader);
            } catch (Throwable t) {
                log(Log.WARN, TAG, stage + ": camera layout " + profile.name()
                        + " does not match: " + t);
                continue;
            }
            try {
                hook(listMethod(profile, loader)).intercept(chain -> {
                    Object result = chain.proceed();
                    addFilms(result, FILMS, resolved);
                    return result;
                });
            } catch (Throwable t) {
                log(Log.WARN, TAG, stage + ": cannot hook " + profile.listClass() + "."
                        + profile.listMethod() + ": " + t);
                continue;
            }
            hooked = true;
            log(Log.INFO, TAG, stage + ": camera layout " + profile.name() + ", hooked "
                    + profile.listClass() + "." + profile.listMethod());
            installPanelHook(profile, loader);
            installNameIconHook();
            installLooksHook(profile, loader, resolved);
            installOrderHook(profile, loader);
            return;
        }
        log(Log.WARN, TAG, stage + ": the camera's filter code is not one this module knows "
                + "(a different camera version?); the camera is left unchanged");
        problems.add(MSG_UNSUPPORTED);
    }

    private static java.lang.reflect.Method listMethod(Profile profile, ClassLoader loader)
            throws ReflectiveOperationException {
        return Class.forName(profile.listClass(), false, loader)
                .getDeclaredMethod(profile.listMethod());
    }

    // Looks up everything a profile names. Throws if any of it is missing.
    private static Resolved resolve(Profile profile, ClassLoader loader)
            throws ReflectiveOperationException {
        listMethod(profile, loader);
        Class<?> item = Class.forName(profile.itemClass(), false, loader);
        Constructor<?> create = item.getDeclaredConstructor(int.class, int.class, int.class,
                int.class, int.class, int.class, String.class);
        create.setAccessible(true);
        Field ordinal = field(item, profile.ordinalField());
        Field cloud = field(item, profile.cloudField());
        return new Resolved(profile, create, ordinal, cloud,
                optionalClass(profile.stringIds(), loader), optionalClass(profile.imageIds(), loader));
    }

    private static Field field(Class<?> cls, String name) throws NoSuchFieldException {
        Field field = cls.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private static Class<?> optionalClass(String name, ClassLoader loader) {
        try {
            return Class.forName(name, false, loader);
        } catch (Throwable t) {
            return null;
        }
    }

    // The strip puts a filter it does not have yet at position 1, so the films, which the camera
    // does not list itself, would end up first and in reverse. Its own rule for the order is the
    // order of the camera's filter component (the films are last in it): official filters in that
    // order, then the user's own presets. After the strip has reloaded, the module puts it back
    // to that rule, and also once when the camera opens (which repairs a strip an earlier
    // version of the module left in a wrong order).
    private void installOrderHook(Profile profile, ClassLoader loader) {
        try {
            Class<?> workspace = Class.forName(profile.workspaceClass(), false, loader);
            Method reload = null;
            for (Method m : workspace.getDeclaredMethods()) {
                Class<?>[] types = m.getParameterTypes();
                if (m.getReturnType() == boolean.class && types.length == 3
                        && types[0] == int.class && types[1] == android.content.Context.class
                        && types[2] == java.util.concurrent.ConcurrentHashMap.class) {
                    reload = m;
                }
            }
            if (reload == null) {
                throw new NoSuchMethodException("no filter reload method in " + workspace);
            }
            Method init = workspace.getDeclaredMethod(profile.initMethod(), int.class);
            Method getList = workspace.getMethod("getList");
            hook(reload).intercept(chain -> {
                Object result = chain.proceed();
                try {
                    if (Boolean.TRUE.equals(result)) {
                        restoreOrder(getList.invoke(chain.getThisObject()), profile, loader);
                    }
                } catch (Throwable t) {
                    log(Log.ERROR, TAG, "could not order the filter strip", t);
                }
                return result;
            });
            hook(init).intercept(chain -> {
                Object result = chain.proceed();
                try {
                    restoreOrder(getList.invoke(chain.getThisObject()), profile, loader);
                } catch (Throwable t) {
                    log(Log.ERROR, TAG, "could not order the filter strip", t);
                }
                return result;
            });
            log(Log.INFO, TAG, "hooked " + profile.workspaceClass() + "." + reload.getName() + ", "
                    + init.getName() + " (filter strip order)");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook the filter strip order: " + t);
        }
    }

    @SuppressWarnings("unchecked")
    private void restoreOrder(Object listObject, Profile profile, ClassLoader loader)
            throws ReflectiveOperationException {
        if (!(listObject instanceof List<?>) || ((List<?>) listObject).isEmpty()) {
            return;
        }
        List<Object> list = (List<Object>) listObject;
        // The camera's filter component and its items, in the camera's own order
        Object registry = Class.forName(profile.registryClass(), false, loader)
                .getMethod(profile.registryMethod()).invoke(null);
        Object component = registry.getClass().getMethod(profile.lookupMethod(), Class.class)
                .invoke(registry, Class.forName(profile.componentClass(), false, loader));
        List<?> official = (List<?>) component.getClass().getMethod("getItems").invoke(component);
        if (official == null || official.isEmpty()) {
            return;
        }
        Map<Object, Integer> rank = new java.util.IdentityHashMap<>();
        for (int i = 0; i < official.size(); i++) {
            rank.put(official.get(i), i);
        }
        Field data = findField(list.get(0).getClass(), profile.itemDataField());
        Method sortIndex = findMethod(list.get(0).getClass(), profile.sortIndexMethod());
        List<Object> sorted = new ArrayList<>(list);
        sorted.sort((a, b) -> {
            try {
                Integer ra = rank.get(data.get(a));
                Integer rb = rank.get(data.get(b));
                return Integer.compare(ra == null ? Integer.MAX_VALUE : ra,
                        rb == null ? Integer.MAX_VALUE : rb);
            } catch (Throwable t) {
                return 0;
            }
        });
        logStrip(list, data, sorted.equals(list));
        if (sorted.equals(list)) {
            return;
        }
        list.clear();
        list.addAll(sorted);
        for (int i = 0; i < list.size(); i++) {
            sortIndex.invoke(list.get(i), i);
        }
        log(Log.INFO, TAG, "put the filter strip back in the camera's order (" + list.size()
                + " items)");
    }

    // What the strip holds, for the log: its size and the names of its last items
    private void logStrip(List<Object> list, Field data, boolean inOrder) {
        try {
            StringBuilder tail = new StringBuilder();
            Application app = currentApplication();
            Field title = null;
            for (int i = Math.max(0, list.size() - 8); i < list.size(); i++) {
                Object filter = data.get(list.get(i));
                if (filter == null) {
                    tail.append("[no data] ");
                    continue;
                }
                if (title == null) {
                    title = field(filter.getClass(), "l");
                }
                int id = title.getInt(filter);
                String name = id > 0 && app != null
                        ? app.getResources().getResourceEntryName(id) : "none";
                tail.append(name).append(' ');
            }
            log(Log.INFO, TAG, "filter strip: " + list.size() + " items, "
                    + (inOrder ? "in order" : "out of order") + ", last: " + tail);
        } catch (Throwable t) {
            log(Log.WARN, TAG, "could not describe the filter strip: " + t);
        }
    }

    // A field of a class or of one of its superclasses
    private static Field findField(Class<?> cls, String name) throws NoSuchFieldException {
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                return field(c, name);
            } catch (NoSuchFieldException ignored) {
            }
        }
        throw new NoSuchFieldException(name);
    }

    // A one-int-argument method of a class or of one of its superclasses
    private static Method findMethod(Class<?> cls, String name) throws NoSuchMethodException {
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            try {
                Method m = c.getDeclaredMethod(name, int.class);
                m.setAccessible(true);
                return m;
            } catch (NoSuchMethodException ignored) {
            }
        }
        throw new NoSuchMethodException(name);
    }

    private void installPanelHook(Profile profile, ClassLoader loader) {
        try {
            var panel = Class.forName(profile.panelClass(), false, loader);
            var method = panel.getDeclaredMethod(profile.panelMethod());
            if (method.getReturnType() != boolean.class) {
                throw new NoSuchMethodException("does not return boolean");
            }
            hook(method).intercept(chain -> {
                log(Log.INFO, TAG, profile.panelMethod() + " called, returning false (no cloud merge)");
                return Boolean.FALSE;
            });
            log(Log.INFO, TAG, "hooked " + profile.panelClass() + "." + profile.panelMethod());
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook " + profile.panelClass() + "." + profile.panelMethod()
                    + ": " + t);
            problems.add(MSG_PANEL);
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
        private final Bitmap ring;
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        private final Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        private ColorStateList tint;
        private PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;

        LabelDrawable(Bitmap bitmap, Bitmap ring) {
            this.bitmap = bitmap;
            this.ring = ring;
            ringPaint.setAlpha(LABEL_RING_ALPHA);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(ring, null, getBounds(), ringPaint);
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
            ringPaint.setAlpha(alpha * LABEL_RING_ALPHA / 255);
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
        int letterHeight = Math.round(LABEL_GLYPH_HEIGHT_DP * density);
        int letterWidthBasis = Math.round(LABEL_CAP_DP * density);   // glyph widths, weight and spacing are those of this height
        int pitch = Math.round((LABEL_CAP_DP + LABEL_LINE_GAP_DP) * density);   // line 1 top to line 2 top
        int top = Math.round((size - (letterHeight + pitch)) / 2f + LABEL_BLOCK_SHIFT_DP * density);
        float scale = letterWidthBasis / (float) LeicaFont.CAP_HEIGHT;

        // Report the current screen density so the drawables are exactly size x size pixels
        // (a vector of 36 dp is, whatever display-size setting is in use).
        int densityDpi = resources.getDisplayMetrics().densityDpi;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        bitmap.setDensity(densityDpi);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setColor(Color.WHITE);
        drawLine(canvas, paint, LABEL_TOP_LINE, size, top, scale, letterWidthBasis, LABEL_GAP_DP, density, 0);
        drawLine(canvas, paint, label, size, top + pitch, scale, letterHeight, LABEL_NAME_GAP_DP, density, 0);

        // The outline: the same letters spread outwards, in a fixed gray.
        Bitmap ring = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        ring.setDensity(densityDpi);
        Canvas ringCanvas = new Canvas(ring);
        Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        ringPaint.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));
        float spread = LABEL_RING_DP * density;
        drawLine(ringCanvas, ringPaint, LABEL_TOP_LINE, size, top, scale, letterWidthBasis, LABEL_GAP_DP, density, spread);
        drawLine(ringCanvas, ringPaint, label, size, top + pitch, scale, letterHeight, LABEL_NAME_GAP_DP, density, spread);
        return new LabelDrawable(bitmap, ring);
    }

    // One line of text, centred horizontally, with its top edge at y. With a spread, every letter
    // is drawn again at points on circles of that radius around its place, which grows it
    // outwards by that much (the outline).
    private static void drawLine(Canvas canvas, Paint paint, String text, int width, float y,
                                 float scale, float height, float gapDp, float density,
                                 float spread) {
        float cap = LeicaFont.CAP_HEIGHT;
        float gap = gapDp * density;
        float hyphen = HYPHEN_LENGTH * cap * scale;

        float letters = 0;
        for (char c : text.toCharArray()) {
            Bitmap glyph = c == '-' ? null : glyph(c);
            letters += c == '-' ? hyphen : glyph == null ? 0 : glyph.getWidth() * scale;
        }
        // Never wider than the label with its outline: tighten the gap when a text would not fit.
        if (text.length() > 1) {
            float room = width - 1 - 2 * LABEL_RING_DP * density;
            gap = Math.min(gap, (room - letters) / (text.length() - 1));
        }
        float total = letters + gap * (text.length() - 1);
        float x = (width - total) / 2f;
        for (char c : text.toCharArray()) {
            if (c == '-') {
                float thick = LeicaFont.STROKE * scale;
                float mid = y + height / 2f;
                float grown = thick / 2f + spread;
                canvas.drawRoundRect(new RectF(x - spread, mid - grown, x + hyphen + spread,
                        mid + grown), grown, grown, paint);
                x += hyphen;
            } else {
                Bitmap glyph = glyph(c);
                if (glyph != null) {
                    float glyphWidth = glyph.getWidth() * scale;
                    drawSpread(canvas, glyph, x, y, glyphWidth, height, spread, paint);
                    x += glyphWidth;
                }
            }
            x += gap;
        }
    }

    private static void drawSpread(Canvas canvas, Bitmap glyph, float x, float y, float w, float h,
                                   float spread, Paint paint) {
        if (spread <= 0) {
            canvas.drawBitmap(glyph, null, new RectF(x, y, x + w, y + h), paint);
            return;
        }
        for (int ring = 1; ring <= 2; ring++) {
            float radius = spread * ring / 2f;
            for (int step = 0; step < 16; step++) {
                double angle = Math.PI * 2 * step / 16;
                float dx = (float) Math.cos(angle) * radius;
                float dy = (float) Math.sin(angle) * radius;
                canvas.drawBitmap(glyph, null,
                        new RectF(x + dx, y + dy, x + dx + w, y + dy + h), paint);
            }
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

    // The Leica UI lists "Looks" from a separate fixed table. Add the same film items there,
    // filling in the extra fields that panel reads (name icon, sample images).
    private void installLooksHook(Profile profile, ClassLoader loader, Resolved resolved) {
        try {
            var looks = Class.forName(profile.looksClass(), false, loader);
            var method = looks.getDeclaredMethod(profile.looksMethod());
            Class<?> item = resolved.create().getDeclaringClass();
            Field iconField = field(item, profile.iconField());
            Field nameIconField = field(item, profile.nameIconField());
            Field samplesField = field(item, profile.samplesField());
            Field labelsField = field(item, profile.labelsField());
            Field descField = field(item, profile.descField());

            hook(method).intercept(chain -> {
                Object result = chain.proceed();
                try {
                    for (Object entry : addFilms(result, LOOKS_FILMS, resolved)) {
                        int icon = iconField.getInt(entry);
                        nameIconField.setInt(entry, nameIconHooked
                                ? NAME_ICON_BASE | resolved.ordinal().getInt(entry)
                                : LOOKS_NAME_ICON);
                        samplesField.set(entry, new ArrayList<>(List.of(icon, icon, icon)));
                        labelsField.set(entry, new ArrayList<>(List.of(icon, icon, icon)));
                        descField.setInt(entry, 0);
                    }
                } catch (Throwable t) {
                    log(Log.ERROR, TAG, "could not finish Looks items", t);
                }
                return result;
            });
            log(Log.INFO, TAG, "hooked " + profile.looksClass() + "." + profile.looksMethod()
                    + " (Leica Looks)");
        } catch (Throwable t) {
            log(Log.WARN, TAG, "cannot hook " + profile.looksClass() + "." + profile.looksMethod()
                    + ": " + t);
            // Only worth telling the user on a phone that actually uses the Leica UI.
            if ("LCC".equals(systemProperty("ro.theme_customize"))) {
                problems.add(MSG_LEICA);
            }
        }
    }

    private static String systemProperty(String key) {
        try {
            return (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
        } catch (Throwable t) {
            return "";
        }
    }

    // ---- telling the user ----
    // A module has no screen of its own inside the camera, so a problem is shown as a toast the
    // first time the camera starts after a camera update, then not again for that camera version.
    private static final String MSG_UNSUPPORTED =
            "The camera's filter code is different from the layouts this module knows, so no "
                    + "film filters were added. The module needs an update for this camera version.";
    private static final String MSG_PANEL =
            "Some film filters may not show in the filter panel on this camera version.";
    private static final String MSG_LEICA =
            "The Leica Looks list is different on this camera version, so the film filters were "
                    + "not added there.";

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

    // Appends any missing film items to the finished list. Leaves the list alone on any surprise.
    @SuppressWarnings("unchecked")
    private List<Object> addFilms(Object result, Film[] films, Resolved resolved) {
        List<Object> created = new ArrayList<>();
        try {
            if (!(result instanceof List<?>)) {
                return created;
            }
            List<Object> list = (List<Object>) result;
            Set<Integer> present = new HashSet<>();
            for (Object entry : list) {
                present.add(resolved.ordinal().getInt(entry));
            }
            List<String> added = new ArrayList<>();
            for (Film film : films) {
                if (present.contains(film.ordinal())) {
                    continue;
                }
                int name = resourceId(resolved.strings(), film.stringId(), "string");
                int image = resourceId(resolved.images(), film.imageId(), "drawable", "mipmap",
                        "raw");
                Object entry = resolved.create().newInstance(PHOTO_CATEGORY, film.ordinal(), name,
                        image, 0, film.order(), "NORMAL");
                resolved.cloud().setInt(entry, film.cloudId());
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

    // A resource id, from the camera's id class when it has one, otherwise looked up by name.
    private static int resourceId(Class<?> ids, String name, String... types) throws Exception {
        if (ids != null) {
            try {
                return ids.getField(name).getInt(null);
            } catch (Throwable ignored) {
            }
        }
        Application app = currentApplication();
        if (app != null) {
            for (String type : types) {
                int id = app.getResources().getIdentifier(name, type, app.getPackageName());
                if (id != 0) {
                    return id;
                }
            }
        }
        throw new NoSuchFieldException("resource " + name);
    }
}
