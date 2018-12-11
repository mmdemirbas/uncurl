package com.github.mmdemirbas.uncurl

fun parseCommandLine(commandLine: String): List<String> {
    // todo: if needed, provide a mechanism to include quotiation marks in result some way (a custom data class may be returned?)
    val parts = mutableListOf<String>()
    val builder = StringBuilder()
    var terminators = ""
    commandLine.forEach { c ->
        when {
            c in terminators         -> {
                parts += builder.toString()
                builder.clear()
                terminators = ""
            }
            terminators.isNotEmpty() -> builder.append(c)
            c in "\""                -> terminators = "\""
            c in "'"                 -> terminators = "'"
            c in " \t\r\n"           -> terminators = ""
            else                     -> {
                builder.append(c)
                terminators = " \t\r\n"
            }
        }
    }
    if (terminators.isNotEmpty()) {
        parts += builder.toString()
        if (terminators.isNotBlank()) System.err.println("Missing delimiter at end: $terminators")
    }
    return parts
}