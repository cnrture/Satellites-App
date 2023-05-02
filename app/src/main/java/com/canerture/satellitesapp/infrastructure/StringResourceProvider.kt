package com.canerture.satellitesapp.infrastructure

interface StringResourceProvider {
    fun getString(stringResId: Int): String
}