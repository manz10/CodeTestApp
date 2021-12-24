package com.example.fusiontestapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

const val API = "API"
const val DESCRIPTION = "Description"
const val AUTH = "Auth"
const val HTTPS = "HTTPS"
const val CORS = "Cors"
const val LINK = "Link"
const val CATEGORY = "Category"

data class EntriesModel(
    @SerializedName(API)
    var api: String? = null,

    @SerializedName(DESCRIPTION)
    var description: String? = null,

    @SerializedName(AUTH)
    var auth: String? = null,

    @SerializedName(HTTPS)
    var isHttps: Boolean? = null,

    @SerializedName(CORS)
    var isCors: String? = null,

    @SerializedName(LINK)
    var link: String? = null,

    @SerializedName(CATEGORY)
    var category: String? = null
) : Serializable