package com.unitsendtest.domain

import com.unitsendtest.domain.data.ImageRepository
import com.unitsendtest.domain.model.Image
import javax.inject.Inject

class FetchImages @Inject constructor(private val imageRepository: ImageRepository) {

    suspend operator fun invoke(): List<Image> = imageRepository.fetchImages().sortedBy { it.title }
}