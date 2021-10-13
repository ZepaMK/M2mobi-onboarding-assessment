package com.unitsendtest.data.image

import com.m2mobi.utility.test.CoroutinesExtension
import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.data.image.mapper.CommentMapper.toComment
import com.unitsendtest.data.image.mapper.ImageMapper
import com.unitsendtest.data.image.mapper.ImageMapper.toImage
import com.unitsendtest.data.image.network.ImageApiService
import com.unitsendtest.data.image.network.response.CommentResponse
import com.unitsendtest.data.image.network.response.ImageResponse
import com.unitsendtest.data.image.storage.ImageDataStore
import com.unitsendtest.domain.model.Image
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, CoroutinesExtension::class, EasyRandomExtension::class)
internal class RemoteImageRepositoryTest {

    @RelaxedMockK
    lateinit var imageApiService: ImageApiService

    @RelaxedMockK
    lateinit var imageDataStore: ImageDataStore

    @InjectMockKs
    lateinit var remoteImageRepository: RemoteImageRepository

    @Test
    fun `When fetchImages, return a list of images from apiService`(
        @Random image: Image,
    ) = runBlockingTest {
        //given
        mockkObject(ImageMapper)
        val response: ImageResponse = mockk {
            every { toImage() } returns image
        }
        coEvery { imageApiService.getImages() } returns listOf(response)

        //when
        val result = remoteImageRepository.fetchImages()

        //then
        result assertEquals listOf(image)
        unmockkObject(ImageMapper)
    }

    @Test
    fun `When fetchImages, store a list of images and a timestamp in the ImageDataStore`(
        @Random images: List<ImageResponse>,
    ) = runBlockingTest {
        //given
        coEvery { imageApiService.getImages() } returns images

        //when
        remoteImageRepository.fetchImages()

        //then
        verify { imageDataStore.cache = images.map { it.toImage() } }
    }

    @Test
    fun `When fetchImages fails, get a list of images from the ImageDataStore`(
        @Random images: List<Image>,
    ) = runBlockingTest {
        //given
        val error = Exception()
        coEvery { imageApiService.getImages() } throws error
        every { imageDataStore.cache } returns images

        //when
        val result = remoteImageRepository.fetchImages()

        //then
        result assertEquals images
    }

    @Test
    fun `When fetchImages fails and cache is empty or not valid, throw and exception error`(
    ) = runBlockingTest {
        //given
        val error = Exception()
        coEvery { imageApiService.getImages() } throws error
        every { imageDataStore.cache } returns null

        //when -> then
        assertThrows<Exception> { remoteImageRepository.fetchImages() }
    }

    @Test
    fun `When fetchComments, return a list of images from apiService`(
        @Random comments: List<CommentResponse>
    ) = runBlockingTest {
        //given
        val id = 1
        coEvery { imageApiService.getComments(id) } returns comments

        //when
        val result = remoteImageRepository.fetchComments(id)

        //then
        result assertEquals comments.map { it.toComment() }
    }

    @Test
    fun `When fetchComments fails, throw and exception error`(
        @Random comments: List<CommentResponse>
    ) = runBlockingTest {
        //given
        val id = 1
        val error = Exception()
        coEvery { imageApiService.getComments(id) } throws error

        //when -> then
        assertThrows<Exception> { remoteImageRepository.fetchComments(id) }
    }
}