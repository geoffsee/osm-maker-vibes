package org.example

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import java.io.File

class ConfigTest {

    @Test
    fun testConfigDeserialization() {
        val jsonString = """
        {
          "osmData": {
            "useLocalExtract": false,
            "localFilePath": "virginia.osm.pbf",
            "boundingBox": {
              "south": 37.115,
              "west": -76.396,
              "north": 37.139,
              "east": -76.345,
              "description": "Poquoson, VA"
            },
            "overpassTimeout": 25
          },
          "projection": {
            "origin": {
              "latitude": 37.120907,
              "longitude": -76.333694
            }
          },
          "output": {
            "fileName": "municipality.glb",
            "autoOpen": true
          }
        }
        """.trimIndent()

        val config = Json.decodeFromString<Config>(jsonString)

        assertNotNull(config)
        assertEquals(false, config.osmData.useLocalExtract)
        assertEquals("virginia.osm.pbf", config.osmData.localFilePath)
        assertEquals(37.115, config.osmData.boundingBox.south)
        assertEquals(-76.396, config.osmData.boundingBox.west)
        assertEquals(37.139, config.osmData.boundingBox.north)
        assertEquals(-76.345, config.osmData.boundingBox.east)
        assertEquals("Poquoson, VA", config.osmData.boundingBox.description)
        assertEquals(25, config.osmData.overpassTimeout)
        assertEquals(37.120907, config.projection.origin.latitude)
        assertEquals(-76.333694, config.projection.origin.longitude)
        assertEquals("municipality.glb", config.output.fileName)
        assertEquals(true, config.output.autoOpen)
    }

    @Test
    fun testJsoncWithSingleLineComments() {
        val jsoncString = """
        {
          // This is a comment about OSM data configuration
          "osmData": {
            "useLocalExtract": false, // Use remote data instead
            "localFilePath": "virginia.osm.pbf",
            "boundingBox": {
              "south": 37.115,
              "west": -76.396,
              "north": 37.139,
              "east": -76.345,
              "description": "Poquoson, VA" // A small city in Virginia
            },
            "overpassTimeout": 25 // Timeout in seconds
          },
          "projection": {
            "origin": {
              "latitude": 37.120907, // Center latitude
              "longitude": -76.333694 // Center longitude
            }
          },
          "output": {
            "fileName": "municipality.glb", // Output file name
            "autoOpen": true // Automatically open the file
          }
        }
        """.trimIndent()

        val config = parseJsonc(jsoncString)

        assertNotNull(config)
        assertEquals(false, config.osmData.useLocalExtract)
        assertEquals("virginia.osm.pbf", config.osmData.localFilePath)
        assertEquals("Poquoson, VA", config.osmData.boundingBox.description)
        assertEquals(25, config.osmData.overpassTimeout)
        assertEquals("municipality.glb", config.output.fileName)
        assertEquals(true, config.output.autoOpen)
    }

    @Test
    fun testJsoncWithMultiLineComments() {
        val jsoncString = """
        {
          /* 
           * OSM Data Configuration
           * This section configures how OSM data is obtained
           */
          "osmData": {
            "useLocalExtract": false,
            "localFilePath": "virginia.osm.pbf",
            "boundingBox": {
              "south": 37.115,
              "west": -76.396,
              "north": 37.139,
              "east": -76.345,
              "description": "Poquoson, VA"
            },
            "overpassTimeout": 25
          },
          /* Projection settings */
          "projection": {
            "origin": {
              "latitude": 37.120907,
              "longitude": -76.333694
            }
          },
          /*
           * Output configuration
           * Controls how the final file is generated
           */
          "output": {
            "fileName": "municipality.glb",
            "autoOpen": true
          }
        }
        """.trimIndent()

        val config = parseJsonc(jsoncString)

        assertNotNull(config)
        assertEquals(false, config.osmData.useLocalExtract)
        assertEquals("Poquoson, VA", config.osmData.boundingBox.description)
        assertEquals("municipality.glb", config.output.fileName)
    }

    @Test
    fun testJsoncWithMixedComments() {
        val jsoncString = """
        {
          // Single line comment at the top
          "osmData": {
            /* Multi-line comment
               about local extract */
            "useLocalExtract": false, // Inline comment
            "localFilePath": "virginia.osm.pbf",
            "boundingBox": {
              "south": 37.115, // Southern boundary
              "west": -76.396,
              "north": 37.139,
              "east": -76.345,
              "description": "Poquoson, VA"
            },
            "overpassTimeout": 25
          },
          "projection": {
            "origin": {
              "latitude": 37.120907,
              "longitude": -76.333694
            }
          },
          "output": {
            "fileName": "municipality.glb",
            "autoOpen": true
          }
          // Final comment
        }
        """.trimIndent()

        val config = parseJsonc(jsoncString)

        assertNotNull(config)
        assertEquals(false, config.osmData.useLocalExtract)
        assertEquals("Poquoson, VA", config.osmData.boundingBox.description)
    }

    @Test
    fun testLoadActualJsoncFile() {
        val configFile = File("config.jsonc")
        if (!configFile.exists()) {
            println("Skipping test - config.jsonc file not found")
            return
        }

        val configText = configFile.readText()
        val config = parseJsonc(configText)

        assertNotNull(config)
        assertEquals(false, config.osmData.useLocalExtract)
        assertEquals("virginia.osm.pbf", config.osmData.localFilePath)
        assertEquals("Poquoson, VA", config.osmData.boundingBox.description)
        assertEquals(25, config.osmData.overpassTimeout)
        assertEquals(37.120907, config.projection.origin.latitude)
        assertEquals(-76.333694, config.projection.origin.longitude)
        assertEquals("municipality.glb", config.output.fileName)
        assertEquals(true, config.output.autoOpen)

        println("✅ Successfully loaded and parsed config.jsonc file with comments!")
    }
}
