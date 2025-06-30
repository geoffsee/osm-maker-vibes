package org.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Config(
    val osmData: OsmDataConfig,
    val projection: ProjectionConfig,
    val output: OutputConfig
)

@Serializable
data class OsmDataConfig(
    val useLocalExtract: Boolean,
    val localFilePath: String,
    val boundingBox: BoundingBoxConfig,
    val overpassTimeout: Int
)

@Serializable
data class BoundingBoxConfig(
    val south: Double,
    val west: Double,
    val north: Double,
    val east: Double,
    val description: String
)

@Serializable
data class ProjectionConfig(
    val origin: OriginConfig
)

@Serializable
data class OriginConfig(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class OutputConfig(
    val fileName: String,
    val autoOpen: Boolean
)

/**
 * Parses JSONC (JSON with Comments) content by removing comments and parsing as regular JSON
 */
fun parseJsonc(jsoncContent: String): Config {
    val cleanedJson = removeJsoncComments(jsoncContent)
    return Json.decodeFromString<Config>(cleanedJson)
}

/**
 * Removes both single-line (//) and multi-line (/* */) comments from JSONC content
 */
private fun removeJsoncComments(content: String): String {
    val result = StringBuilder()
    var i = 0
    var inString = false
    var escaped = false

    while (i < content.length) {
        val char = content[i]

        when {
            // Handle escape sequences in strings
            escaped -> {
                result.append(char)
                escaped = false
                i++
            }
            // Handle string boundaries
            char == '"' && !escaped -> {
                inString = !inString
                result.append(char)
                i++
            }
            // Handle escape character
            char == '\\' && inString -> {
                escaped = true
                result.append(char)
                i++
            }
            // Skip comments when not in string
            !inString && char == '/' && i + 1 < content.length -> {
                when (content[i + 1]) {
                    // Single-line comment
                    '/' -> {
                        // Skip until end of line
                        i += 2
                        while (i < content.length && content[i] != '\n') {
                            i++
                        }
                        // Keep the newline for proper formatting
                        if (i < content.length) {
                            result.append('\n')
                            i++
                        }
                    }
                    // Multi-line comment
                    '*' -> {
                        // Skip until */
                        i += 2
                        while (i + 1 < content.length && !(content[i] == '*' && content[i + 1] == '/')) {
                            i++
                        }
                        if (i + 1 < content.length) {
                            i += 2 // Skip the closing */
                        }
                    }
                    else -> {
                        result.append(char)
                        i++
                    }
                }
            }
            // Regular character
            else -> {
                result.append(char)
                i++
            }
        }
    }

    return result.toString()
}
