package com.unitsendtest.domain

import com.m2mobi.utility.test.CoroutinesExtension
import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.domain.data.ImageRepository
import com.unitsendtest.domain.model.Image
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, CoroutinesExtension::class, EasyRandomExtension::class)
internal class FetchImagesTest {

    @RelaxedMockK
    lateinit var imageRepository: ImageRepository

    @InjectMockKs
    lateinit var fetchImages: FetchImages

    @Test
    fun `When invoked, return sorted list of images from repository`(
        @Random images: List<Image>,
    ) = runBlocking {
        // given
        coEvery { imageRepository.fetchImages() } returns images

        //when
        val result = fetchImages()

        //then
        result assertEquals images.sortedBy { it.title }
    }
}