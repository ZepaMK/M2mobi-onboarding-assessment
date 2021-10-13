package com.unitsendtest.data.image.storage

import com.unitsendtest.data.image.storage.model.ImageCacheData
import com.unitsendtest.domain.model.Image
import java.sql.Timestamp
import javax.inject.Inject

class MemoryImageDataStore @Inject constructor() : ImageDataStore {

    private var _cache: ImageCacheData? = null
    override var cache: List<Image>?
        get() = validateCache()?.image
        set(value) {
            _cache = value?.let { ImageCacheData(Timestamp(System.currentTimeMillis()), it) }
        }

    private fun validateCache(): ImageCacheData? = _cache?.run {
        if (Timestamp(System.currentTimeMillis()).time - timeStamp.time > CACHE_TIME_LIMIT) {
            _cache = null
        }
        _cache
    }

    companion object {

        const val CACHE_TIME_LIMIT = 30000L
    }
}