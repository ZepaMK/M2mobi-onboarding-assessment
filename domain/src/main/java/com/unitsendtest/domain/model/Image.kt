package com.unitsendtest.domain.model

import java.io.Serializable

data class Image(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
) : Serializable