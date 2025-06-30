package org.example

import org.osm2world.O2WConverter
import org.osm2world.map_data.creation.OSMToMapDataConverter
import org.osm2world.math.geo.LatLon
import org.osm2world.math.geo.MetricMapProjection
import org.osm2world.math.geo.OrthographicAzimuthalMapProjection
import org.osm2world.osm.creation.OSMFileReader
import org.osm2world.osm.creation.OverpassReader
import org.osm2world.output.gltf.GltfOutput
import java.awt.Desktop
import java.io.File

fun main() {

    /* ----------------------------------------------------------------
       1)  GET THE OSM DATA
       ---------------------------------------------------------------- */

    val useLocalExtract = false             // <- flip to true if you have a .osm.pbf on disk

    val osmData = if (useLocalExtract) {
        // A) Read from a downloaded extract (fast, offline)
        OSMFileReader(File("virginia.osm.pbf")).getAllData()

    } else {
        // B) Live Overpass pull (fresh, great for small/medium areas)
        val bbox = "37.115,-76.396,37.139,-76.345"         // south,west,north,east   (≈ Poquoson, VA)
        val query = """
            [out:xml][timeout:25];
            (
              node($bbox);
              way($bbox);
              relation($bbox);
            );
            out body;
            >;
            out skel qt;
        """.trimIndent()

        OverpassReader().getData(query)
    }

    /* ----------------------------------------------------------------
       2)  CONVERT TO MapData, THEN TO 3-D GEOMETRY
       ---------------------------------------------------------------- */

    val origin = LatLon(37.120907, -76.333694)

    val projection = OrthographicAzimuthalMapProjection(origin)
    val mapData   = OSMToMapDataConverter(projection).createMapData(osmData, null)

    val o2w       = O2WConverter()
    val output    = File("municipality.glb")
    o2w.convert(mapData, null, GltfOutput(output))

    println("Generated ${output.absolutePath}")

    /* ----------------------------------------------------------------
       3)  OPEN IT IN THE DEFAULT VIEWER
       ---------------------------------------------------------------- */

    try {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(output)
                println("Opening ${output.name} in default viewer …")
            } else {
                println("Desktop OPEN action not supported on this system.")
            }
        } else {
            println("Desktop is not supported on this system.")
        }
    } catch (e: Exception) {
        println("Could not open file automatically: ${e.message}")
    }
}
