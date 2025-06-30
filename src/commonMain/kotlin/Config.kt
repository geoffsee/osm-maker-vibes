package org.example

import kotlinx.serialization.Serializable

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