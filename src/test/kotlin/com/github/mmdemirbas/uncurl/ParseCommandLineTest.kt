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

    @ParameterizedTest
    @MethodSource
    fun `test parseCommand`(pair: Pair<RawCommand, ParsedCommand>) {
        assertEquals(pair.second, parseCommand(pair.first))
    }

    fun `test parseCommand`() = listOf(listOf("curl").parsedAs("curl"),
                                       listOf("curl", "https://google.com").parsedAs("curl", Argument.Name("https://google.com")),
                                       listOf("curl", "-H", "Set-Cookie: 123").parsedAs("curl",
                                                                                        Argument.ShortOption('H'),
                                                                                        Argument.Name("Set-Cookie: 123")),
                                       listOf("curl", "-v", "-H", "Set-Cookie: 123").parsedAs("curl",
                                                                                              Argument.ShortOption('v'),
                                                                                              Argument.ShortOption('H'),
                                                                                              Argument.Name("Set-Cookie: 123")),
                                       listOf("curl", "-vH", "Set-Cookie: 123").parsedAs("curl",
                                                                                         Argument.ShortOption('v'),
                                                                                         Argument.ShortOption('H'),
                                                                                         Argument.Name("Set-Cookie: 123")))

    private fun List<String>.parsedAs(name: String, vararg arguments: Argument) = RawCommand(this) to ParsedCommand(name, arguments.asList())

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ParameterizedTest
    @MethodSource
    fun `test tokenizeCommand`(pair: Pair<String, List<RawCommand>>) {
        assertEquals(pair.second, tokenizeCommand(pair.first))
    }

    fun `test tokenizeCommand`() = listOf("".hasTokens(),
                                          "singleWord".hasTokens(listOf("singleWord")),
                                          "'single-quoted command' args".hasTokens(listOf("single-quoted command", "args")),
                                          "\"double-quoted command\" args".hasTokens(listOf("double-quoted command", "args")),
                                          "\"single quote ' inside double quotes\" args".hasTokens(listOf("single quote ' inside double quotes",
                                                                                                          "args")),
                                          "\'double quote \" inside single quotes\' args".hasTokens(listOf("double quote \" inside single quotes",
                                                                                                           "args")),
                                          " \t \t leadingWhitespace".hasTokens(listOf("leadingWhitespace")),
                                          "trailingWhitespace \t \t ".hasTokens(listOf("trailingWhitespace")),
                                          "empty-args \"\"".hasTokens(listOf("empty-args", "")),
                                          "empty-args ''".hasTokens(listOf("empty-args", "")),
                                          "blank-args \" \t \n \r \"".hasTokens(listOf("blank-args", " \t \n \r ")),
                                          "blank-args ' \t \n \r '".hasTokens(listOf("blank-args", " \t \n \r ")),
                                          "'missing single-quote".hasTokens(listOf("missing single-quote")),
                                          "\"missing double-quote".hasTokens(listOf("missing double-quote")),
                                          "curl 'https://google.com' -H \"Connection: keep-alive\" -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7' --compressed".hasTokens(
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
                                          "single quote '\\'' escaped -quoted".hasTokens(listOf("single", "quote", "'", "escaped", "-quoted")),
                                          "double quote \"\\\"\" escaped -quoted".hasTokens(listOf("double", "quote", "\"", "escaped", "-quoted")),
                                          "single quote \\' escaped -bare".hasTokens(listOf("single", "quote", "'", "escaped", "-bare")),
                                          "double quote \\\" escaped -bare".hasTokens(listOf("double", "quote", "\"", "escaped", "-bare")),
                                          "white\\\tspace\\ escaped\\\nincluding\\\rnewline".hasTokens(listOf("white\tspace escaped\nincluding\rnewline")),
                                          "multiple\ncommands".hasTokens(listOf("multiple"), listOf("commands")),
                                          "\t \r\t \n\t \r\t \nblank\t \n\t \r\t \n\r\t \nlines\r\t \n\t \r\t \n\t ".hasTokens(listOf("blank"),
                                                                                                                               listOf("lines")),
                                          "semicolon;as ;command\t;separator".hasTokens(listOf("semicolon"),
                                                                                        listOf("as"),
                                                                                        listOf("command"),
                                                                                        listOf("separator")))

    private fun String.hasTokens(vararg multipleCommandParts: List<String>) = this to multipleCommandParts.map { RawCommand(it) }
}
