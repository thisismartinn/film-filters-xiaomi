# Film Filters (Camera)

LSPosed/Vector module (libxposed API 101-102) that adds the film filters R600, P100F, F-50 (shown
as "Reversal") and KC64 (shown as "Orchid") to the Xiaomi Camera filter panel. KP160 is already
listed by the camera as "Monsoon". The LUTs are part of the camera app
(`lut_normal_r_600`, `lut_normal_p_100f`, `lut_normal_f_50`, `lut_normal_c_64`).

Target: `com.android.camera` **6.7.000280.0** (obfuscated build). The hooks use that build's
class names; a camera update will most likely need them re-resolved.

## Hooks

| Target (6.7.000280.0) | Purpose |
| --- | --- |
| `Ii.i0.d()` | Photo filter list (standard UI): appends the missing film items. |
| `com.android.camera.fragment.m0.ut()` | Panel: returns `false` so the list is not merged with the cloud list, which dropped the added items. |
| `E9.h.c()` | Leica "Looks" list (Leica UI): appends the same items. Experimental. |
| `com.xiaomi.camera.effect.EffectController.q`, `s2.a.n`, `E9.h.c` | Log-only: list size and ordinals at each stage. |

## Use

1. Install the module, enable it in the manager, scope: Camera only.
2. Force-stop Camera and reopen it.
3. Standard UI: `su -c "setprop camera.debug.theme_customize.disabled true"` (resets on reboot).

Logs go to logcat, tag `FilmFilters`:
`su -c "logcat -d | grep FilmFilters"`. (They do not appear in the manager's module log.)

## Build

`./gradlew assembleRelease` needs the Android SDK (compileSdk 37, build-tools 37.0.0); put
`sdk.dir=...` in `local.properties`. The release build is signed with the debug key.
