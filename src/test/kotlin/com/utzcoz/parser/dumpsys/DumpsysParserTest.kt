package com.utzcoz.parser.dumpsys

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class DumpsysParserTest {
    val outContent = ByteArrayOutputStream()
    val originOutContent = System.out

    @Before
    fun setUp() {
        System.setOut(PrintStream(outContent))
    }

    @After
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
                    surfaceflinger: parse dumpsys SurfaceFlinger result

                SUB-COMMANDS
                
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
        val args = Array(1) { "-p" }
        DumpsysParser.main(args)
        val prints =
            """
                DumpsysParser arguments [-p]

                Please use -p to select parser to parse content
                
            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithoutMinusMinus() {
        val args = Array(2) { index -> if (index == 0) "-p" else "surfaceflinger" }
        DumpsysParser.main(args)
        val prints =
            """
                DumpsysParser arguments [-p, surfaceflinger]

                Please use -- to separate sub-commands for specific parser
                
            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithCompleteSurfaceFlingerCommand() {
        val args = Array(3) { index ->
            if (index == 0) "-p" else if (index == 1) "surfaceflinger" else "--"
        }
        DumpsysParser.main(args)
        val prints =
            """
                DumpsysParser arguments [-p, surfaceflinger, --]


            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }

    @Test
    fun testWithUnsupportedParser() {
        val args = Array(3) { index ->
            if (index == 0) "-p" else if (index == 1) "unsupported-parser" else "--"
        }
        DumpsysParser.main(args)
        val prints =
            """
                DumpsysParser arguments [-p, unsupported-parser, --]

                We don't support parser unsupported-parser now.

            """.trimIndent()
        assertEquals(prints, outContent.toString())
    }
}