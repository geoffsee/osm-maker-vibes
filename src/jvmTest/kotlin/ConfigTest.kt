package org.example

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
}