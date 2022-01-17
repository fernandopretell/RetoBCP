package com.fulbiopretell.retobcp.util

import android.content.Context
import com.fulbiopretell.core.models.CurrencyLocalModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object Util {

    fun getCountryCode(context: Context): List<CurrencyLocalModel> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("currencies.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {}

        val listCountryType = object : TypeToken<List<CurrencyLocalModel>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }
}