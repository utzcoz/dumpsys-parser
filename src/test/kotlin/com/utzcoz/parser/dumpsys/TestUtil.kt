package com.utzcoz.parser.dumpsys

import java.nio.file.Path
import java.nio.file.Paths

class TestUtil {
    companion object {
        fun getDumpsysSurfaceFlingerTestFilePath(): String {
            val path: Path =
                Paths.get("src", "test", "resources", "aosp-9-normal-dumpsys-surfaceflinger-result")
            return path.toFile().absolutePath
        }
    }
}