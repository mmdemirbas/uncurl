package com.github.mmdemirbas.uncurl

fun parseCommandLine(commandLine: String): List<String> {
    // todo: if needed, provide a mechanism to include quotiation marks in result some way (a custom data class may be returned?)
    val parts = mutableListOf<String>()
    val builder = StringBuilder()
    // todo: implement multiple commands?
    var terminators = ""
    var escaped = false
    commandLine.forEach { c ->
        when {
            escaped               -> {
                builder.append(c)
                escaped = false
            }
            terminators.isEmpty() -> when (c) {
                '\\'         -> {
                    escaped = true
                    terminators = " \t\r\n"
                }
                '\"'         -> terminators = "\""
                '\''         -> terminators = "'"
                in " \t\r\n" -> terminators = ""
                else         -> {
                    builder.append(c)
                    terminators = " \t\r\n"
                }
            }
            else                  -> when (c) {
                '\\'           -> escaped = true
                in terminators -> {
                    parts += builder.toString()
                    builder.clear()
                    terminators = ""
                }
                else           -> builder.append(c)
            }
        }
    }
    if (terminators.isNotEmpty()) {
        parts += builder.toString()
        // todo: handle missing terminator
        if (terminators.isNotBlank()) System.err.println("Missing delimiter at end: $terminators")
    }
    return parts
}