package com.fulbiopretell.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyLocalModel(
    @SerializedName("name") @Expose
    val name: String,
    @SerializedName("currency") @Expose
    val currency: CurrencyData,
    @SerializedName("flag") @Expose
    val flag: String
)

data class CurrencyData(
    @SerializedName("code") @Expose
    val code: String,
    @SerializedName("name") @Expose
    val name: String,
    @SerializedName("symbol") @Expose
    val symbol: String
)
