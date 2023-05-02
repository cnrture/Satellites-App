package com.canerture.satellitesapp.infrastructure

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class StringResourceProviderImpl @Inject constructor(
    private val context: Context
) : StringResourceProvider {

    override fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}