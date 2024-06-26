package com.utzcoz.parser.dumpsys

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class DumpsysParserTest {
    private val outContent = ByteArrayOutputStream()
    private val originOutContent: PrintStream = System.out

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originOutContent)
    }

    @Test
    fun testHelpCommand() {
        val args = Array(1) { "-h" }
        DumpsysParser.main(args)
        val helpCommandPrints =
            """
            DumpsysParser arguments [-h]

            Dumpsys result parser

            Show help info: java -jar dumpsys-result-parser.jar -h

            Parse specific info: java -jar dumpsys-result-parser.jar -p PARSE-NAME -- SUB-COMMANDS

            PARSE-NAME:
                ${SurfaceFlingerParser.SF_PARSER_NAME}: parse dumpsys SurfaceFlinger result

            SUB-COMMANDS
                ${SurfaceFlingerParser.SF_PARSER_NAME}:
                    ${SurfaceFlingerParser.BUFFER_LAYER_TREE_COMMAND} show buffer layer tree
            
            """.trimIndent()
        assertEquals(helpCommandPrints, outContent.toString())
    }

    @Test
    fun testWithoutParserCommand() {
        val args = Array(0) { "-p" }
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments []

            Please use -p to select parser to parse content
            
            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithMinusPOnly() {
        val args = arrayOf("-p")
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments [-p]

            Please use -p to select parser to parse content
            
            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithMinusFOnly() {
        val filePath = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath()
        val args = arrayOf("-f", filePath)
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments [-f, $filePath]

            Please use -p to select parser to parse content
            
            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithoutMinusMinus() {
        val filePath = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath()
        val parseName = SurfaceFlingerParser.SF_PARSER_NAME
        val args = arrayOf("-p", parseName, "-f", filePath)
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments [-p, $parseName, -f, $filePath]

            Please use -- to separate sub-commands for specific parser
            
            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithNonExistFile() {
        val filePath = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath() + "non-exist"
        val parseName = SurfaceFlingerParser.SF_PARSER_NAME
        val args = arrayOf("-p", parseName, "-f", filePath, "--")
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments [-p, $parseName, -f, $filePath, --]

            Please provide exist file with -f

            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithCompleteSurfaceFlingerCommand() {
        val filePath = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath()
        val parseName = SurfaceFlingerParser.SF_PARSER_NAME
        val args = arrayOf("-p", parseName, "-f", filePath, "--")
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments [-p, $parseName, -f, $filePath, --]


            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithUnsupportedParser() {
        val parseName = "unsupported-parser"
        val filePath = TestUtil.getAOSP9DumpsysSurfaceFlingerTestFilePath()
        val args = arrayOf("-p", parseName, "-f", filePath, "--")
        DumpsysParser.main(args)
        val prints =
            """
            DumpsysParser arguments [-p, $parseName, -f, $filePath, --]

            We don't support parser unsupported-parser now.

            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }
}
