package com.unitsendtest.domain

import com.unitsendtest.domain.data.ImageRepository
import com.unitsendtest.domain.model.Comment
import kotlinx.coroutines.delay
import javax.inject.Inject

class FetchComments @Inject constructor(private val imageRepository: ImageRepository) {

    suspend operator fun invoke(id: Int): List<Comment> = imageRepository.fetchComments(id)
        .sortedBy { it.id }
        .take(LIMIT)

    companion object {

        const val LIMIT = 20
    }
}