package com.github.mmdemirbas.uncurl

/**
 * @author Muhammed Demirbaş
 * @since 2018-12-15 01:04
 */
fun uncurl(command: ParsedCommand): Uncurl {
    // todo: handle --data content & decide http method accordingly
    // todo: support both long and short options
    // todo: check missing switch property at the end case
    // todo: check urls contains exactly one url

    val urls = mutableListOf<String>()
    val headers = mutableListOf<String>()
    val parts = command.arguments.iterator()
    while (parts.hasNext()) {
        val arg = parts.next()
        when (arg) {
            is Argument.Name        -> urls += arg.name
            is Argument.ShortOption -> {
                when (arg.name) {
                    'H'  -> headers += (parts.next() as Argument.Name).name
                    else -> TODO("unsupported short option: ${arg.name}")
                }
            }
            is Argument.LongOption  -> TODO("unsupported long option: ${arg.name}")
        }
    }
    return Uncurl(method = HttpMethod.GET, url = urls.single(), headers = headers)
}


sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
}

data class Uncurl(val method: HttpMethod, val url: String, val headers: List<String>)
