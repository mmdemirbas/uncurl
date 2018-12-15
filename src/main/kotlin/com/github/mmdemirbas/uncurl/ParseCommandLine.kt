package com.github.mmdemirbas.uncurl


data class ParsedCommand(val name: String, val arguments: List<Argument>)

sealed class Argument {
    data class Name(val name: String) : Argument()
    data class ShortOption(val name: Char) : Argument()
    data class LongOption(val name: String) : Argument()
}

data class RawCommand(val parts: List<String>)


fun parseCommand(rawCommand: RawCommand) = ParsedCommand(name = rawCommand.parts[0], arguments = rawCommand.parts.drop(1).flatMap {
    when {
        it.startsWith("--") -> listOf(Argument.LongOption(it))
        it.startsWith("-")  -> it.toCharArray().drop(1).map { Argument.ShortOption(it) }
        else                -> listOf(Argument.Name(it))
    }
})

fun tokenizeCommand(snippet: String): List<RawCommand> {
    val commands = mutableListOf<RawCommand>()
    val tokens = mutableListOf<String>()
    val builder = StringBuilder()
    var terminators = ""
    var escaped = false
    snippet.forEach { c ->
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
                        if (tokens.isNotEmpty()) commands += RawCommand(tokens.toList())
                        tokens.clear()
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
                        tokens += builder.toString()
                        builder.clear()
                        terminators = ""
                        if (c in "\r\n;") {
                            commands += RawCommand(tokens.toList())
                            tokens.clear()
                        }
                    }
                    else           -> builder.append(c)
                }
            }
        }
    }
    if (terminators.isNotEmpty()) {
        tokens += builder.toString()
        // todo: handle missing terminator
        if (terminators.isNotBlank()) System.err.println("Missing delimiter at end: $terminators")
    }
    if (tokens.isNotEmpty()) commands += RawCommand(tokens.toList())
    return commands.toList()
}
