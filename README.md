# Xiaomi Camera Mod

Two LSPosed/Vector modules (libxposed API 101-102) for the Xiaomi Camera on HyperOS. Each does one job, installs on its own and has its own package, so you can use either or both.

| Module | What it does | Package |
| ------ | ------------ | ------- |
| [HyperFilmFilters](app/README.md) | Brings the film filters R600, P100F, F-50 and KC64 (and KP160 in the Leica UI) back to the filter panel and the filter strip, in the standard UI and the Leica UI. | `io.martin.hyperfilters` |
| [HyperCameraMod](cameramod/README.md) | Turns on three features the camera already contains: the Palette and Parameters panel, the Original colour style, and the Leica Essential CMOS style. | `io.martin.hypercameramod` |

Both are developed and tested on the Xiaomi 17 Ultra (Leica edition) with Xiaomi Camera 6.7 on HyperOS 4. They do not check the camera's version number: they look for what they need and, when it is not there, leave the camera unchanged and say so. Each module's README lists what it looks for.

## Use

1. Install the module you want and enable it in LSPosed or Vector. Set its scope to **Camera** only.
2. Force-stop Camera and open it again.

If the camera misbehaves, disable the module and force-stop Camera to restore the stock behaviour. Nothing on the system partition is changed.

## Build

A Gradle project with two modules. It needs the Android SDK (compileSdk 37, build-tools 37.0.0); put `sdk.dir=...` in `local.properties`, then:

```bash
./gradlew assembleRelease
```

or `:app:assembleRelease` / `:cameramod:assembleRelease` for one module. The release builds are signed with the debug key.

## Disclaimer

Unofficial community modules, not affiliated with Xiaomi. They hook internal camera code and are provided as is, for personal use. Use them at your own risk.
