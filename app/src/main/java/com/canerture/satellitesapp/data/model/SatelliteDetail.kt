package com.canerture.satellitesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.canerture.satellitesapp.common.Constants.Name
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "satellite_detail")
data class SatelliteDetail(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerialName(Name.COST_PER_LAUNCH)
    val costPerLaunch: Long,
    @SerialName(Name.FIRST_FLIGHT)
    val firstFlight: String,
    val height: Int,
    val mass: Long
)
