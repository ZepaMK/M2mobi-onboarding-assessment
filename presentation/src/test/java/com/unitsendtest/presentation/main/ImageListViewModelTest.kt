package com.unitsendtest.presentation.main

import com.jraska.livedata.test
import com.m2mobi.utility.test.CoroutinesExtension
import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.android.ArchComponentsExtension
import com.unitsendtest.domain.FetchImages
import com.unitsendtest.domain.model.Image
import com.unitsendtest.presentation.generic.UIState
import com.unitsendtest.presentation.main.ImagesMapper.toSections
import com.unitsendtest.presentation.main.model.ImageListNavAction
import com.unitsendtest.presentation.main.model.ImageUIModel
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(
    MockKExtension::class,
    EasyRandomExtension::class,
    CoroutinesExtension::class,
    ArchComponentsExtension::class
)
internal class ImageListViewModelTest {

    @RelaxedMockK
    lateinit var fetchImages: FetchImages

    @InjectMockKs
    lateinit var imageListViewModel: ImageListViewModel

    @Test
    fun `When observing images, fetch images and show UIState`(
        @Random images: List<Image>
    ) {
        //given
        coEvery { fetchImages() } returns images

        //when
        val resultState = imageListViewModel.imagesState.test()
        val result = imageListViewModel.images.test()

        //then
        result.assertValue(images.toSections())
        resultState.assertValueHistory(UIState.NORMAL, UIState.LOADING, UIState.NORMAL)
    }

    @Test
    fun `When observing images fails, the UIState should be error`(
    ) {
        //given
        val error = Exception()
        coEvery { fetchImages() } throws error

        //when
        val resultState = imageListViewModel.imagesState.test()
        imageListViewModel.images.test()

        //then
        resultState.assertValueHistory(UIState.NORMAL, UIState.LOADING, UIState.ERROR)
    }

    @Test
    fun `When refresh is clicked, fetch images and show UIState`(
        @Random images: List<Image>
    ) {
        //given
        coEvery { fetchImages() } returns images

        //when
        val result = imageListViewModel.images.test()
        result.assertValue(images.toSections())
        val resultState = imageListViewModel.imagesState.test()
        imageListViewModel.onRefreshClicked()

        //then
        result.assertValue(images.toSections())
        resultState.assertValueHistory(UIState.NORMAL, UIState.LOADING, UIState.NORMAL)
    }

    @Test
    fun `When refresh fails, show error state`() {
        // given
        coEvery { fetchImages() } throws Throwable()

        // when
        val result = imageListViewModel.imagesState.test()
        imageListViewModel.onRefreshClicked()

        // then
        result.assertValue(UIState.ERROR)
    }

    @Test
    fun `When clicking on image, navigate to detail`(
        @Random imageUIModel: ImageUIModel
    ) {
        //when
        val result = imageListViewModel.navigation.test()
        imageListViewModel.onImageClicked(imageUIModel)

        //then
        result.assertValue(ImageListNavAction.OpenImageDetails(imageUIModel))
    }
}