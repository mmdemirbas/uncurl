package com.github.mmdemirbas.uncurl

/**
 * @author Muhammed Demirbaş
 * @since 2018-12-15 01:04
 */
fun uncurl(command: List<String>): Uncurl {
    // todo: handle --data content
    // todo: support both long and short options
    // todo: check missing switch property at the end case
    // todo: check urls contains exactly one url

    val urls = mutableListOf<String>()
    val headers = mutableListOf<String>()
    val it = command.drop(1).iterator()
    while (it.hasNext()) {
        val arg = it.next()
        when (arg) {
            "-H"           -> headers += it.next()
            "--compressed" -> TODO()
            else           -> urls += arg
        }
    }
    return Uncurl(method = HttpMethod.GET, url = urls.single(), headers = headers)
}

sealed class CommandPart {
    data class CommandName(val name: String) : CommandPart()
}

sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
}

data class Uncurl(val method: HttpMethod, val url: String, val headers: List<String>)
