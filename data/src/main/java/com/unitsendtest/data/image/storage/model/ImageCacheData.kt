package com.unitsendtest.data.image.storage.model

import com.unitsendtest.domain.model.Image
import java.sql.Timestamp

data class ImageCacheData(
    val timeStamp: Timestamp,
    val image: List<Image>,
)