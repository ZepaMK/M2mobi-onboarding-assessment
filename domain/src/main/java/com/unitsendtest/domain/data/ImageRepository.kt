package com.unitsendtest.domain.data

import com.unitsendtest.domain.model.Comment
import com.unitsendtest.domain.model.Image

interface ImageRepository {

    suspend fun fetchImages(): List<Image>
    suspend fun fetchComments(id: Int): List<Comment>
}