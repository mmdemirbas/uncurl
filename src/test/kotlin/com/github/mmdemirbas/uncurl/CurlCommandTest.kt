package com.github.mmdemirbas.uncurl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author Muhammed Demirbaş
 * @since 2018-12-15 01:05
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurlCommandTest {
    @ParameterizedTest
    @MethodSource
    fun `test parseCurlCommand`(pair: Pair<ParsedCommand, CurlCommand>) = assertEquals(pair.second, parseCurlCommand(pair.first))

    private fun `test parseCurlCommand`() = listOf(listOf("curl", "https://google.com").gives(CurlCommand(method = HttpMethod.GET,
                                                                                                          url = "https://google.com",
                                                                                                          headers = listOf())),
                                                   listOf("curl",
                                                          "https://giris.odtuteknokent.com.tr/media/1.jpg",
                                                          "-H",
                                                          "Pragma: no-cache",
                                                          "-H",
                                                          "Accept-Encoding: gzip, deflate, br",
                                                          "-H",
                                                          "Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7",
                                                          "-H",
                                                          "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36",
                                                          "-H",
                                                          "Accept: image/webp,image/apng,image/*,*/*;q=0.8",
                                                          "-H",
                                                          "Referer: https://giris.odtuteknokent.com.tr/Account/SignIn?ReturnUrl=%2f",
                                                          "-H",
                                                          "Cookie: ASP.NET_SessionId=abcdef; __RequestVerificationToken=abcdef",
                                                          "-H",
                                                          "Connection: keep-alive",
                                                          "-H",
                                                          "Cache-Control: no-cache").gives(CurlCommand(method = HttpMethod.GET,
                                                                                                       url = "https://giris.odtuteknokent.com.tr/media/1.jpg",
                                                                                                       headers = listOf("Pragma: no-cache",
                                                                                                                        "Accept-Encoding: gzip, deflate, br",
                                                                                                                        "Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7",
                                                                                                                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36",
                                                                                                                        "Accept: image/webp,image/apng,image/*,*/*;q=0.8",
                                                                                                                        "Referer: https://giris.odtuteknokent.com.tr/Account/SignIn?ReturnUrl=%2f",
                                                                                                                        "Cookie: ASP.NET_SessionId=abcdef; __RequestVerificationToken=abcdef",
                                                                                                                        "Connection: keep-alive",
                                                                                                                        "Cache-Control: no-cache"))))

    private fun List<String>.gives(expected: CurlCommand) = parseCommand(RawCommand(this)) to expected
}