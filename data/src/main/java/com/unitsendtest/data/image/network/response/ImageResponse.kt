package com.unitsendtest.data.image.network.response

import com.google.gson.annotations.SerializedName
import com.unitsendtest.domain.model.Image

data class ImageResponse(

    @SerializedName("albumId")
    val albumId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,
)