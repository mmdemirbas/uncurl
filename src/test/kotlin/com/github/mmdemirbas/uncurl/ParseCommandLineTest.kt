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

    fun `test parseCommandLine`() = listOf("".gives(),
                                           "singleWord".gives("singleWord"),
                                           "'single-quoted command' args".gives("single-quoted command", "args"),
                                           "\"double-quoted command\" args".gives("double-quoted command", "args"),
                                           "\"single quote ' inside double quotes\" args".gives("single quote ' inside double quotes", "args"),
                                           "\'double quote \" inside single quotes\' args".gives("double quote \" inside single quotes", "args"),
                                           " \t \t leadingWhitespace".gives("leadingWhitespace"),
                                           "trailingWhitespace \t \t ".gives("trailingWhitespace"),
                                           "empty-args \"\"".gives("empty-args", ""),
                                           "empty-args ''".gives("empty-args", ""),
                                           "blank-args \" \t \n \r \"".gives("blank-args", " \t \n \r "),
                                           "blank-args ' \t \n \r '".gives("blank-args", " \t \n \r "),
                                           "'missing single-quote".gives("missing single-quote"),
                                           "\"missing double-quote".gives("missing double-quote"),
                                           "single quote '\\'' escaped -quoted".gives("single", "quote", "'", "escaped", "-quoted"),
                                           "double quote \"\\\"\" escaped -quoted".gives("double", "quote", "\"", "escaped", "-quoted"),
                                           "single quote \\' escaped -bare".gives("single", "quote", "'", "escaped", "-bare"),
                                           "double quote \\\" escaped -bare".gives("double", "quote", "\"", "escaped", "-bare"),
                                           "white\\\tspace\\ escaped\\\nincluding\\\rnewline".gives("white\tspace escaped\nincluding\rnewline"),
                                           "curl 'https://google.com' -H \"Connection: keep-alive\" -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7' --compressed".gives(
                                                   "curl",
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
                                                   "--compressed"))

    private fun String.gives(vararg expected: String) = TestCase(this, expected.asList())
}
