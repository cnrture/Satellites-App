package com.canerture.satellitesapp.data.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Satellite(
    val id: Int,
    val active: Boolean,
    val name: String
) : Parcelable {
    companion object {
        fun fromJson(jsonString: String): Satellite? {
            return try {
                Gson().fromJson(jsonString, Satellite::class.java)
            } catch (e: Exception) {
                return null
            }
        }
    }
}
