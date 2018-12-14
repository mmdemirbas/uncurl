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
    data class TestCase(val input: String, val expected: List<List<String>>)

    @ParameterizedTest
    @MethodSource
    fun TestCase.`test parseCommandLine`() = assertEquals(expected, parseCommandLine(input))

    fun `test parseCommandLine`() = listOf("".gives(),
                                           "singleWord".gives(listOf("singleWord")),
                                           "'single-quoted command' args".gives(listOf("single-quoted command", "args")),
                                           "\"double-quoted command\" args".gives(listOf("double-quoted command", "args")),
                                           "\"single quote ' inside double quotes\" args".gives(listOf("single quote ' inside double quotes",
                                                                                                       "args")),
                                           "\'double quote \" inside single quotes\' args".gives(listOf("double quote \" inside single quotes",
                                                                                                        "args")),
                                           " \t \t leadingWhitespace".gives(listOf("leadingWhitespace")),
                                           "trailingWhitespace \t \t ".gives(listOf("trailingWhitespace")),
                                           "empty-args \"\"".gives(listOf("empty-args", "")),
                                           "empty-args ''".gives(listOf("empty-args", "")),
                                           "blank-args \" \t \n \r \"".gives(listOf("blank-args", " \t \n \r ")),
                                           "blank-args ' \t \n \r '".gives(listOf("blank-args", " \t \n \r ")),
                                           "'missing single-quote".gives(listOf("missing single-quote")),
                                           "\"missing double-quote".gives(listOf("missing double-quote")),
                                           "curl 'https://google.com' -H \"Connection: keep-alive\" -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7' --compressed".gives(
                                                   listOf("curl",
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
                                                          "--compressed")),
                                           "single quote '\\'' escaped -quoted".gives(listOf("single", "quote", "'", "escaped", "-quoted")),
                                           "double quote \"\\\"\" escaped -quoted".gives(listOf("double", "quote", "\"", "escaped", "-quoted")),
                                           "single quote \\' escaped -bare".gives(listOf("single", "quote", "'", "escaped", "-bare")),
                                           "double quote \\\" escaped -bare".gives(listOf("double", "quote", "\"", "escaped", "-bare")),
                                           "white\\\tspace\\ escaped\\\nincluding\\\rnewline".gives(listOf("white\tspace escaped\nincluding\rnewline")),
                                           "multiple\ncommands".gives(listOf("multiple"), listOf("commands")),
                                           "\t \r\t \n\t \r\t \nblank\t \n\t \r\t \n\r\t \nlines\r\t \n\t \r\t \n\t ".gives(listOf("blank"),
                                                                                                                            listOf("lines")))

    private fun String.gives(vararg multipleCommandParts: List<String>) = TestCase(this, multipleCommandParts.asList())
}
