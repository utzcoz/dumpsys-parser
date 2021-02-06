# `dumpsys-parser`

`dumpsys-parser` is a tool to parse the `adb shell dumpsys` result of `AOSP` device.

It's self learning project for `Kotlin`, and useful for self development of `AOSP` frameworks.

## Help

Use following command to show help information:

```shell
java -jar dumpsys-parser-0.2-all.jar -h
```

## `SurfaceFlinger`

Firstly, we should use `adb shell dumpsys SurfaceFlinger | tee surfaceflinger-result.txt` to
dump `SurfaceFlinger` with `dumpsys` and store the result to file `surfaceflinger-result.txt`.

Then we should use following command to parse `SurfaceFlinger` result:
 ```shell
 java -jar dumpsys-parser-0.2-all.jar -p surfaceflinger -f surfaceflinger-result.txt -- SUBCOMMDS
```

Currently, we only support `-bl-tree` as sub command. And it will following result:

```
|-- Display Overlays#0, isOpaque false, region Rect(0, 0, 3840, 3840)
`-- Display Root#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |-- com.android.server.wm.DisplayContent$TaskStackContainers@8b527d1#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |-- Stack=0#0, isOpaque false, region Rect(0, 0, 1920, 1080)
    |   |   `-- Task=43#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |       `-- AppWindowToken{67b5d6b token=Token{637baba ActivityRecord{5470fe5 u0 com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity t43}}}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |           `-- 2e7b096 com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |               `-- com.farmerbb.taskbar.androidx86/com.farmerbb.taskbar.activity.HomeActivity#0, isOpaque false, region Rect(0, 0, 1920, 1080)
    |   |-- animationLayer#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |-- boostedAnimationLayer#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |-- homeAnimationLayer#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   `-- splitScreenDividerAnchor#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |-- mAboveAppWindowsContainers#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |-- WindowToken{1e95e8e android.os.BinderProxy@ff44e89}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |   `-- b00b6af AssistPreviewPanel#0, isOpaque false, region Rect(0, 1080, 3840, 4920)
    |   |-- WindowToken{4f36f0f android.os.BinderProxy@b4c3c6e}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |   `-- c170b9c com.farmerbb.taskbar.androidx86#0, isOpaque false, region Rect(0, 1008, 3840, 4848)
    |   |-- WindowToken{85a4589 android.os.BinderProxy@3ead890}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |   `-- 95898e DockedStackDivider#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |-- WindowToken{8d12250 android.os.BinderProxy@d49a013}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |   `-- 3602c49 NavigationBar#0, isOpaque false, region Rect(0, 1008, 3840, 4848)
    |   |       `-- NavigationBar#0, isOpaque false, region Rect(0, 1008, 1920, 1080)
    |   |-- WindowToken{9c004 android.os.BinderProxy@e781417}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |   `-- 3c029ed com.farmerbb.taskbar.androidx86#0, isOpaque false, region Rect(0, 918, 3840, 4758)
    |   |       `-- #0, isOpaque false, region Rect(0, 918, 1920, 1008)
    |   |-- WindowToken{a22180c android.os.BinderProxy@aa273f}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |   `-- 9201555 StatusBar#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   |       `-- StatusBar#0, isOpaque false, region Rect(0, 0, 1920, 36)
    |   `-- WindowToken{b01ed2b android.os.BinderProxy@703b17a}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |       `-- 78b6b46 com.farmerbb.taskbar.androidx86#0, isOpaque false, region Rect(0, 36, 3840, 3876)
    |-- mBelowAppWindowsContainers#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |   `-- WallpaperWindowToken{2817237 token=android.os.Binder@9213336}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |       `-- 8b85d8d com.android.systemui.ImageWallpaper#0, isOpaque false, region Rect(0, 0, 3840, 3840)
    |           `-- com.android.systemui.ImageWallpaper#0, isOpaque true, region Rect(-480, -740, 2400, 1820)
    `-- mImeWindowsContainers#0, isOpaque false, region Rect(0, 0, 3840, 3840)
        `-- WindowToken{3827d8c android.os.Binder@a5eb2bf}#0, isOpaque false, region Rect(0, 0, 3840, 3840)
``` 

It will print the `tree` like-style for `layer`, what will help to analyze the `layer`'s size
and it's parent `layer` size to analyze the wrong `layer` size for `multi-window`.