# Xiaomi 17 Ultra: features that cannot be enabled from the camera app

Findings from camera 6.7.000280.0 on HyperOS 4.0.5.0 (Xiaomi 17 Ultra). Each is limited by the phone's camera hardware layer (HAL), not by the camera app, so a module cannot turn it on. Recheck after a firmware update.

| Feature | Result | Evidence |
| ------- | ------ | -------- |
| Leica Essential CMOS (M10R) | The style can be selected, but the capture is an ordinary picture: no Leica processing, no `legendmode` EXIF tag (0x88b0), so the Gallery shows no LEICA button. | The HAL (`camera.qcom.core.so`, `com.qti.chi.override.so`) has only `Legend`, `LegendMonopan` and `LeicaMoment`; no M10R or CMOS. Session parameter `com.xiaomi.sessionparams.legendMode` 1 (M9) and 2 (M3) get a processed picture; 3 does not. The camera app's own style list (`s2.A`) has M10R compiled out. |
| Leica Essential bokeh lens and aperture (35/75 mm) | Not available. | `getBokehCapsInfo` and `com.xiaomi.legendMode.capsInfo` are absent from the HAL. |
| Open Gate video | Not available. | The camera shows the `open_gate` ratio only when the characteristic `com.xiaomi.camera.supportedfeatures.videoOpenGate` is true. The HAL does not define it, and its libraries have no open-gate code. |
| Master portrait label on Original style photos | The Gallery's own label; not changed. | Gallery side. |

Already working, nothing to enable: Log video and Master cinema mode (`videologformat`, `videologBitformat`, `cinelook`).

## How to recheck after an update

Check the HAL for a style or feature name:

```bash
su -c 'for f in /vendor/lib64/hw/camera.qcom.core.so /vendor/lib64/hw/camera.qcom.so /vendor/lib64/hw/com.qti.chi.override.so; do echo "== $f"; strings -a $f | grep -iE "m10r|cmos|legend|leica|opengate|open_gate" | sort -u; done'
```

List the camera features the HAL defines:

```bash
su -c "dumpsys media.camera | grep -ao 'supportedfeatures\.[A-Za-z0-9_]*' | sort -u"
```

If `videoOpenGate` shows up, or the libraries name an M10R/CMOS style, the feature may be possible; HyperCameraMod keeps the CMOS entry for that case (`setprop camera.feature.m10r 1`).
