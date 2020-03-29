package com.utzcoz.parser.dumpsys

class DumpsysParser {

    companion object {
        private const val spaceIndent = "    "
        private const val surfaceFlingerParserName = "surfaceflinger"

        @JvmStatic
        fun main(args: Array<String>) {
            val list = args.toList()
            println("DumpsysParser arguments $list")
            println()
            if (list.isNotEmpty() && list[0] == "-h") {
                printHelpInfo()
                return
            }
            if (list.size < 2 || list[0] != "-p") {
                println("Please use -p to select parser to parse content")
                return
            }
            val parser = list[1]
            if (list.size < 3 || list[2] != "--") {
                println("Please use -- to separate sub-commands for specific parser")
                return
            }
            val subCommands = args.slice(3 until args.size)
            when (parser) {
                surfaceFlingerParserName -> SurfaceFlingerParser.parse(subCommands)
                else -> {
                    println("We don't support parser $parser now.")
                }
            }
        }

        private fun printHelpInfo() {
            println("Dumpsys result parser")
            println()
            println("Show help info: java -jar dumpsys-result-parser.jar -h")
            println()
            println("Parse specific info: java -jar dumpsys-result-parser.jar -p PARSE-NAME -- SUB-COMMANDS")
            println()
            println("PARSE-NAME:")
            println("$spaceIndent$surfaceFlingerParserName: parse dumpsys SurfaceFlinger result")
            println()
            println("SUB-COMMANDS")
            SurfaceFlingerParser.showSupportCommands(spaceIndent)
        }
    }
}