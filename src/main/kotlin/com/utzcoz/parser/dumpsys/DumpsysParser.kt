package com.utzcoz.parser.dumpsys

import java.io.File

class DumpsysParser {
    companion object {
        private const val SPACE_INDENT = "    "
        private const val SF_PARSER_NAME = "surfaceflinger"

        @JvmStatic
        fun main(args: Array<String>) {
            val list = args.toList()
            println("DumpsysParser arguments $list")
            println()
            if (list.isNotEmpty() && list[0] == "-h") {
                printHelpInfo()
                return
            }
            if (list.size < 2 || !list.contains("-p")) {
                println("Please use -p to select parser to parse content")
                return
            }
            val parser = list[list.indexOf("-p") + 1]
            if (list.size < 4 || !list.contains("-f")) {
                println("Please use -f to provide dumpsys file")
                return
            }
            val filePath = list[list.indexOf("-f") + 1]
            val file = File(filePath)
            if (!file.exists()) {
                println("Please provide exist file with -f")
                return
            }
            if (list.size < 5 || list[4] != "--") {
                println("Please use -- to separate sub-commands for specific parser")
                return
            }
            val subCommands = args.slice(5 until args.size)
            when (parser) {
                SF_PARSER_NAME ->
                    SurfaceFlingerParser.parse(subCommands, file.readText())
                else ->
                    println("We don't support parser $parser now.")
            }
        }

        private fun printHelpInfo() {
            println("Dumpsys result parser")
            println()
            println("Show help info: java -jar dumpsys-result-parser.jar -h")
            println()
            println(
                "Parse specific info: java -jar dumpsys-result-parser.jar -p " +
                    "PARSE-NAME -- SUB-COMMANDS",
            )
            println()
            println("PARSE-NAME:")
            println("$SPACE_INDENT$SF_PARSER_NAME: parse dumpsys SurfaceFlinger result")
            println()
            println("SUB-COMMANDS")
            SurfaceFlingerParser.showSupportCommands(SPACE_INDENT)
        }
    }
}
