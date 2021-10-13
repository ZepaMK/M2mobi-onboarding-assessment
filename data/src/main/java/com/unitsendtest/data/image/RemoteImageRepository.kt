package com.unitsendtest.data.image

import com.unitsendtest.data.image.network.ImageApiService
import com.unitsendtest.data.image.mapper.CommentMapper.toComment
import com.unitsendtest.data.image.mapper.ImageMapper.toImage
import com.unitsendtest.data.image.storage.ImageDataStore
import com.unitsendtest.domain.data.ImageRepository
import com.unitsendtest.domain.model.Comment
import com.unitsendtest.domain.model.Image
import javax.inject.Inject

class RemoteImageRepository @Inject constructor(
    private val imageApiService: ImageApiService,
    private val imageDataStore: ImageDataStore,
) : ImageRepository {

    override suspend fun fetchImages(): List<Image> {
        return try {
            imageApiService.getImages()
                .map { it.toImage() }
                .also { imageDataStore.cache = it }
        } catch (error: Exception) {
            imageDataStore.cache ?: throw error
        }
    }

    override suspend fun fetchComments(id: Int): List<Comment> {
        return imageApiService.getComments(id).map { it.toComment() }
    }
}