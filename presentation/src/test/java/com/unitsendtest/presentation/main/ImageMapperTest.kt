package com.unitsendtest.presentation.main

import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.domain.model.Image
import com.unitsendtest.presentation.main.ImagesMapper.toSections
import com.unitsendtest.presentation.main.ImagesMapper.toUIModel
import com.unitsendtest.presentation.main.ImagesMapper.toUIModels
import com.unitsendtest.presentation.main.model.ImageSection
import com.unitsendtest.presentation.main.model.ImageUIModel
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EasyRandomExtension::class)
class ImageMapperTest {

    @Test
    fun `Image to ImageUIModel mapping`(
        @Random image: Image
    ) {
        //when
        val result = image.toUIModel()

        //then
        result assertEquals ImageUIModel(
            image = image,
            albumId = image.albumId,
            id = image.id,
            title = image.title,
            imageUrl = image.url,
            thumbnailUrl = image.thumbnailUrl,
        )
    }

    @Test
    fun `List of Images to list of ImageUIModel's mapping`(
        @Random images: List<Image>
    ) {
        //when
        val result = images.toUIModels()

        //then
        result assertEquals images.map { it.toUIModel() }
    }

    @Test
    fun `List of Images to list of ImageSection's mapping`(
        @Random images: List<Image>
    ) {
        //when
        val result = images.toSections()

        //then
        result assertEquals images.groupBy { it.title.first().uppercaseChar() }
            .map { (header, images) ->
                ImageSection(header.toString(), images.toUIModels())
            }
    }
}