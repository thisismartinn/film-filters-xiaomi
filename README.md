# Film Filters for Xiaomi Camera

An LSPosed/Vector module (libxposed API 101-102) that brings back the film filters from old Xiaomi Camera to the new Xiaomi Camera v6 filter panel.

## What it does

Adds film filters that the camera's current filter list leaves out. In the standard UI:

| Filter | Shown in the panel as |
| ------ | --------------------- |
| R600 | R600 |
| P100F | P100F |
| F-50 | Reversal |
| KC64 | Orchid |

KP160 is already in the standard panel as "Monsoon", so the module leaves it alone there; the Leica UI list does not have it, so the module adds it. The LUTs for all five are already part of the camera app; the module only makes them appear in the list.

Status, for camera **6.7.000280.0** (from HyperOS 4.0.5.0 on Xiaomi 17 Ultra) only:

- Standard UI: works.
- Leica UI ("Looks" panel): works.

The standard panel shows F-50 as "Reversal" and KC64 as "Orchid", the camera's own display names. The Leica UI lists all five filters (R600, P100F, F-50, KC64, KP160) by their film codes.

## Camera versions

The film filters come from older camera builds, and their code is still present in the build the module targets.

| Camera version | From | Notes |
| -------------- | ---- | ----- |
| 5.3.000460.0 | HyperOS 2 | Has the film filters in its photo filter list. |
| 6.3.007010.0 | HyperOS 3 | Same filter code; class names are not obfuscated. |
| 6.7.000280.0 | HyperOS 4.0.5.0 | The build the module targets; class names are obfuscated. |

The filter LUTs are byte-identical across these builds.

## How it works

1. **Add the items.** The camera builds its photo filter list in `Ii.i0.d()`. The module hooks that method and appends the four missing items, using the camera's own names and thumbnails.
2. **Skip the cloud merge.** The filter panel merges the local list with the cloud filter list and drops any local filter the cloud list does not name. The module makes `com.android.camera.fragment.m0.ut()` return `false`, so the panel uses the local list as it is.
3. **Leica UI.** The Leica UI builds its own "Looks" list in `E9.h.c()`. The module appends the five filters there too.

| Target (6.7.000280.0) | Purpose |
| --------------------- | ------- |
| `Ii.i0.d()` | Photo filter list (standard UI): appends R600, P100F, F-50 and KC64. |
| `com.android.camera.fragment.m0.ut()` | Filter panel: returns `false` so the list is not merged with the cloud list. |
| `E9.h.c()` | Leica "Looks" list: appends R600, P100F, F-50, KC64 and KP160. |
| `com.xiaomi.camera.effect.EffectController.q`, `s2.a.n`, `E9.h.c` | Log only: list size and filter ordinals at each stage. |

The class names come from the obfuscated 6.7 build, so a camera update will probably need them looked up again. When a hook can't be installed the module logs a warning and leaves the camera unchanged.

## Use

1. Install the module and enable it in LSPosed or Vector. Set its scope to **Camera** only.
2. Force-stop Camera and open it again.
3. For the standard UI on a phone that uses the Leica UI, turn the Leica theme off. This resets on reboot:

   ```bash
   su -c "setprop camera.debug.theme_customize.disabled true"
   ```

   Set it back to `false` and force-stop Camera to return to the Leica UI.

If the camera misbehaves, disable the module and force-stop Camera to restore the stock behaviour. Nothing on the system partition is changed.

## Logs

The module logs to logcat under the tag `FilmFilters`, not to the manager's module log:

```bash
su -c "logcat -d | grep FilmFilters"
```

Useful lines:

- `hooked Ii.i0.d`, `hooked ...m0.ut`: the hooks are installed.
- `filter list: N items, added [...]`: the items were added.
- `stored list` and `restoreItems`: the list size and filter ordinals at each stage (81 to 84 are the added filters).
- `cannot hook ...`: the camera build differs from 6.7.000280.0.

## Build

A Gradle project. It needs the Android SDK (compileSdk 37, build-tools 37.0.0); put `sdk.dir=...` in `local.properties`, then:

```bash
./gradlew assembleRelease
```

The release build is signed with the debug key.

## Disclaimer

An unofficial community module, not affiliated with Xiaomi. It hooks internal camera code and is provided as is, for personal use. Use it at your own risk.
