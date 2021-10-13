package com.unitsendtest.presentation.main

import com.unitsendtest.domain.model.Image
import com.unitsendtest.presentation.main.model.ImageSection
import com.unitsendtest.presentation.main.model.ImageUIModel

object ImagesMapper {

    fun List<Image>.toUIModels(): List<ImageUIModel> {
        return map { it.toUIModel() }
    }

    fun Image.toUIModel(): ImageUIModel {
        return ImageUIModel(this, albumId, id, title, url, thumbnailUrl)
    }

    fun List<Image>.toSections(): List<ImageSection> {
        return groupBy { it.title.first().uppercaseChar() }
            .map { (header, images) ->
                ImageSection(header.toString(), images.toUIModels())
            }
    }
}