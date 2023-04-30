package com.canerture.satellitesapp.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition

@Database(
    entities = [SatelliteDetail::class, SatellitePosition::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(SatelliteTypeConverters::class)
abstract class SatellitesDatabase : RoomDatabase() {
    abstract fun getSatelliteDao(): SatellitesDao
}