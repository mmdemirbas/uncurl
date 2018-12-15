package com.github.mmdemirbas.uncurl

fun parseCommandLine(commandLine: String): List<List<String>> {
    val lines = mutableListOf<List<String>>()
    val words = mutableListOf<String>()
    val builder = StringBuilder()
    var terminators = ""
    var escaped = false
    commandLine.forEach { c ->
        when {
            escaped               -> {
                builder.append(c)
                escaped = false
            }
            terminators.isEmpty() -> {
                when (c) {
                    '\\'       -> {
                        escaped = true
                        terminators = " \t\r\n;"
                    }
                    '\"'       -> terminators = "\""
                    '\''       -> terminators = "'"
                    in " \t"   -> terminators = ""
                    in "\r\n;" -> {
                        if (words.isNotEmpty()) lines += words.toList()
                        words.clear()
                    }
                    else       -> {
                        builder.append(c)
                        terminators = " \t\r\n;"
                    }
                }
            }
            else                  -> {
                when (c) {
                    '\\'           -> escaped = true
                    in terminators -> {
                        words += builder.toString()
                        builder.clear()
                        terminators = ""
                        if (c in "\r\n;") {
                            lines += words.toList()
                            words.clear()
                        }
                    }
                    else           -> builder.append(c)
                }
            }
        }
    }
    if (terminators.isNotEmpty()) {
        words += builder.toString()
        // todo: handle missing terminator
        if (terminators.isNotBlank()) System.err.println("Missing delimiter at end: $terminators")
    }
    if (words.isNotEmpty()) lines += words.toList()
    return lines.toList()
}