package com.unitsendtest.data.image.mapper

import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.data.image.mapper.ImageMapper.toImage
import com.unitsendtest.data.image.network.response.ImageResponse
import com.unitsendtest.domain.model.Image
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EasyRandomExtension::class)
internal class ImageMapperTest {

    @Test
    fun `ImageResponse to Image mapping`(
        @Random imageResponse: ImageResponse
    )  {
        //when
        val result = imageResponse.toImage()

        //then
        result assertEquals Image(
            albumId = imageResponse.albumId,
            id = imageResponse.id,
            title = imageResponse.title,
            url = imageResponse.url,
            thumbnailUrl = imageResponse.thumbnailUrl,
        )
    }
}