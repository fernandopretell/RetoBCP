package com.fulbiopretell.retobcp.models

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("base")
    var base: String? = null,
    @SerializedName("result")
    var result: JsonObject? = null
)
