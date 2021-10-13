package com.unitsendtest.data.image.storage

import com.unitsendtest.domain.model.Image

interface ImageDataStore {

    var cache: List<Image>?
}