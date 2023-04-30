package com.canerture.satellitesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satellite_positions")
data class SatellitePosition(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val positions: List<Position>
)
