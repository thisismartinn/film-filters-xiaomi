# HyperOS Camera Test

Two builds of the Xiaomi HyperOS camera app (`com.android.camera`), shared for side-by-side testing on HyperOS devices.

## Downloads

Both APKs are attached to the [`camera-test` release](https://github.com/thisismartinn/hyperos-camera-test/releases/tag/camera-test) ("v5 & v6 apks").

| Label | Version | File | Size |
| ----- | ------- | ---- | ---- |
| v5 | 5.3.000460.0 | `com.android.camera_5.3.000460.0.apk` | ~165 MB |
| v6 | 6.4.000530.0 | `com.android.camera_6.4.000530.0.apk` | ~195 MB |

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

## Disclaimer

These are third-party app packages from a stock system app. They are provided for personal testing only, and may not run on every device or ROM. Install at your own risk.
