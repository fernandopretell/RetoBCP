package com.fulbiopretell.retobcp.models

import com.google.gson.annotations.SerializedName

data class GetTypeExchange(
    @SerializedName("from")
    var from: String? = null,
    @SerializedName("to")
    var to: String? = null
)
