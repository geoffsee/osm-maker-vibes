package org.example

import kotlinx.serialization.json.Json
import java.io.File

fun main() {
    println("OSM Maker - JSON Configuration Demo")

    /* ----------------------------------------------------------------
       LOAD AND DEMONSTRATE JSON CONFIGURATION
       ---------------------------------------------------------------- */

    val configFile = File("config.json")
    if (!configFile.exists()) {
        println("Error: config.json not found in current directory")
        println("Please ensure config.json exists in the working directory")
        return
    }

    val config = try {
        val configText = configFile.readText()
        Json.decodeFromString<Config>(configText)
    } catch (e: Exception) {
        println("Error reading configuration: ${e.message}")
        return
    }

    println("\n✅ Configuration loaded successfully from config.json!")
    println("📍 Area: ${config.osmData.boundingBox.description}")
    println("📂 Use local extract: ${config.osmData.useLocalExtract}")
    println("📄 Local file path: ${config.osmData.localFilePath}")
    println("⏱️  Overpass timeout: ${config.osmData.overpassTimeout} seconds")
    println("🗺️  Bounding box:")
    println("   South: ${config.osmData.boundingBox.south}")
    println("   West: ${config.osmData.boundingBox.west}")
    println("   North: ${config.osmData.boundingBox.north}")
    println("   East: ${config.osmData.boundingBox.east}")
    println("🎯 Projection origin:")
    println("   Latitude: ${config.projection.origin.latitude}")
    println("   Longitude: ${config.projection.origin.longitude}")
    println("💾 Output file: ${config.output.fileName}")
    println("🚀 Auto-open: ${config.output.autoOpen}")

    // Calculate bounding box area
    val area = (config.osmData.boundingBox.north - config.osmData.boundingBox.south) * 
               (config.osmData.boundingBox.east - config.osmData.boundingBox.west)
    println("📐 Approximate area: $area square degrees")

    // Simulate the workflow that would happen with OSM2World
    println("\n🔄 Simulating OSM processing workflow:")

    if (config.osmData.useLocalExtract) {
        println("1. Would read OSM data from: ${config.osmData.localFilePath}")
    } else {
        val bbox = "${config.osmData.boundingBox.south},${config.osmData.boundingBox.west},${config.osmData.boundingBox.north},${config.osmData.boundingBox.east}"
        println("1. Would fetch OSM data via Overpass API for bbox: $bbox")
        println("   Query timeout: ${config.osmData.overpassTimeout} seconds")
    }

    println("2. Would set projection origin to: ${config.projection.origin.latitude}, ${config.projection.origin.longitude}")
    println("3. Would convert OSM data to 3D geometry")
    println("4. Would generate output file: ${config.output.fileName}")

    if (config.output.autoOpen) {
        println("5. Would automatically open the generated file")
    } else {
        println("5. Auto-open is disabled, file would remain closed")
    }

    println("\n✨ JSON configuration reading implementation complete!")
    println("🎉 The app now successfully reads configuration from JSON instead of using hardcoded values.")
}
