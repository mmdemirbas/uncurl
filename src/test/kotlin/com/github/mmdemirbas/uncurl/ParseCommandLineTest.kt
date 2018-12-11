package com.github.mmdemirbas.uncurl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author Muhammed Demirbaş
 * @since 2018-12-05 22:50
 */
class ParseCommandLineTest {
    @Test
    fun `parseCommandLine works`() {
        parseCommandLine("curl 'https://google.com' -H \"Connection: keep-alive\" -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7' --compressed") equalsTo listOf(
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
                "--compressed")
    }
}

infix fun <T> T.equalsTo(expected: T) {
    assertEquals(expected, this)
}
