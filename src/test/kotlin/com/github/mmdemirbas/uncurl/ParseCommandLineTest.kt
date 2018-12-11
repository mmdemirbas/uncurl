package com.github.mmdemirbas.uncurl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Muhammed Demirbaş
 * @since 2018-12-05 22:50
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParseCommandLineTest {
    data class TestCase(val input: String, val expected: List<String>)

    @ParameterizedTest
    @MethodSource
    fun TestCase.`test parseCommandLine`() = assertEquals(expected, parseCommandLine(input))

    fun `test parseCommandLine`() = listOf(TestCase(input = "", expected = listOf()),
                                           TestCase(input = "singleWord", expected = listOf("singleWord")),
                                           TestCase(input = "'single-quoted command' args", expected = listOf("single-quoted command", "args")),
                                           TestCase(input = "\"double-quoted command\" args", expected = listOf("double-quoted command", "args")),
                                           TestCase(input = "\"single quote ' inside double quotes\" args",
                                                    expected = listOf("single quote ' inside double quotes", "args")),
                                           TestCase(input = "\'double quote \" inside single quotes\' args",
                                                    expected = listOf("double quote \" inside single quotes", "args")),
                                           TestCase(input = " \t \t leadingWhitespace", expected = listOf("leadingWhitespace")),
                                           TestCase(input = "trailingWhitespace \t \t ", expected = listOf("trailingWhitespace")),
                                           TestCase(input = "empty-args \"\"", expected = listOf("empty-args", "")),
                                           TestCase(input = "empty-args ''", expected = listOf("empty-args", "")),
                                           TestCase(input = "blank-args \" \t \n \r \"", expected = listOf("blank-args", " \t \n \r ")),
                                           TestCase(input = "blank-args ' \t \n \r '", expected = listOf("blank-args", " \t \n \r ")),
                                           TestCase(input = "curl 'https://google.com' -H \"Connection: keep-alive\" -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7' --compressed",
                                                    expected = listOf("curl",
                                                                      "https://google.com",
                                                                      "-H",
                                                                      "Connection: keep-alive",
                                                                      "-H",
                                                                      "Pragma: no-cache",
                                                                      "-H",
                                                                      "Cache-Control: no-cache",
                                                                      "-H",
                                                                      "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36",
                                                                      "-H",
                                                                      "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
                                                                      "-H",
                                                                      "Accept-Encoding: gzip, deflate, br",
                                                                      "-H",
                                                                      "Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7",
                                                                      "--compressed")))
}
