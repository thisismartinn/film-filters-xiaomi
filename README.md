# HyperOS Camera Test

Builds of the Xiaomi HyperOS camera app (`com.android.camera`), shared for side-by-side testing on HyperOS devices, plus [`film-filters-module/`](film-filters-module/), an Xposed module that adds film filters to the camera on a Xiaomi 17 Ultra.

## Downloads

The APKs are attached to the [`camera-test` release](https://github.com/thisismartinn/hyperos-camera-test/releases/tag/camera-test) ("v5 & v6 apks").

| Label | Version | File | Size |
| ----- | ------- | ---- | ---- |
| v5 | 5.3.000460.0 | `com.android.camera_5.3.000460.0.apk` | ~165 MB |
| v6 | 6.4.000530.0 | `com.android.camera_6.4.000530.0.apk` | ~195 MB |
| v6 (17 Ultra) | 6.3.007010.0 | `com.android.camera_6.3.007010.0.apk` | ~195 MB |
| v6 (installed on the Xiaomi 17 Ultra Leica edition) | 6.7.000280.0 | `MiuiCamera_running.apk` | ~191 MB |

`filters_v1.0.8.apk` in the same release is a third-party app that bundles LUT filters from several phone makers; it was used as a reference and is not part of the camera.

## Installation

1. Download the APK you want to test from the release page.
2. Copy it to your device, or install over USB with ADB:

   ```bash
   adb install -r com.android.camera_5.3.000460.0.apk
   ```

   Use `-r` to replace an existing install. Downgrading from v6 to v5 may require uninstalling updates first:

   ```bash
   adb shell pm uninstall -k --user 0 com.android.camera
   ```

   Run that uninstall command with care: it removes the camera app for the current user until you reinstall an APK.

3. Open the camera app and grant camera, microphone and storage permissions.

## What to test

- App launches and shows the live preview
- Photo capture and saving to the gallery
- Video recording
- Front and rear camera switching
- Flash, HDR and zoom controls
- Differences in UI and behavior between v5 and v6

## Test notes

Add your results here.

| Device | HyperOS version | App version | Result | Notes |
| ------ | --------------- | ----------- | ------ | ----- |
|        |                 |             |        |       |

## Film Filters module

[`film-filters-module/`](film-filters-module/) is an LSPosed/Vector module (libxposed API 101-102) that adds the old v5 film filters R600, P100F, F-50 (shown as "Reversal") and KC64 (shown as "Orchid") to the camera's filter panel on the Xiaomi 17 Ultra. KP160 is already in the panel as "Monsoon". The LUTs for all five are already inside the v5 and v6 APKs and are byte-identical between them.

Status, for camera **6.7.000280.0** only:

- Standard UI: works. Set `camera.debug.theme_customize.disabled=true` (`su -c "setprop camera.debug.theme_customize.disabled true"`, resets on reboot) to turn the Leica UI off.
- Leica UI ("Looks" panel): experimental and not yet confirmed.

How it works:

- The camera builds its photo filter list in `Ii.i0.d()`. The module appends the four missing items to that list.
- The panel merges that list with the cloud filter list and drops local filters the cloud list does not name. The module makes `com.android.camera.fragment.m0.ut()` return `false` to skip the merge.
- The Leica UI builds its own list in `E9.h.c()`. The module appends the same items there.

The class names come from the obfuscated 6.7 build, so a camera update will probably need them looked up again. Logs go to logcat under the tag `FilmFilters`, not to the manager's module log:

```bash
su -c "logcat -d | grep FilmFilters"
```

See [`film-filters-module/README.md`](film-filters-module/README.md) for the hook table and build steps. The module APKs are not committed; build with `./gradlew assembleRelease`.

## Disclaimer

These are third-party app packages from a stock system app. They are provided for personal testing only, and may not run on every device or ROM. Install at your own risk.
