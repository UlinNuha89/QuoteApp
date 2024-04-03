package com.lynn.quoteapp.data.model

import com.google.gson.annotations.SerializedName

data class Quote (
    val id:String,
    val quote : String,
    val anime : String,
    val character : String
)
