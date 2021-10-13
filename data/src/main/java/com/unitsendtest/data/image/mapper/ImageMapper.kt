package com.unitsendtest.data.image.mapper

import com.unitsendtest.data.image.network.response.ImageResponse
import com.unitsendtest.domain.model.Image

object ImageMapper {

    fun ImageResponse.toImage(): Image {
        return Image(albumId, id, title, url, thumbnailUrl)
    }
}