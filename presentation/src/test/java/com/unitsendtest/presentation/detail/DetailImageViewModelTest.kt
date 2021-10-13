package com.unitsendtest.presentation.detail

import com.jraska.livedata.test
import com.m2mobi.utility.test.CoroutinesExtension
import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.android.ArchComponentsExtension
import com.unitsendtest.domain.FetchComments
import com.unitsendtest.domain.model.Comment
import com.unitsendtest.presentation.detail.CommentsMapper.toUIModels
import com.unitsendtest.presentation.generic.UIState
import com.unitsendtest.presentation.main.model.ImageUIModel
import io.mockk.coEvery
import io.mockk.every
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
internal class DetailImageViewModelTest {

    @RelaxedMockK
    lateinit var fetchComments: FetchComments

    @RelaxedMockK
    lateinit var imageUIModel: ImageUIModel

    @InjectMockKs
    lateinit var detailImageViewModel: DetailImageViewModel

    @Test
    fun `When observing image, fetch image`(
        @Random image: ImageUIModel
    ) {
        //when
        val result = detailImageViewModel.image.test()

        //then
        result.assertValue(imageUIModel)
    }

    @Test
    fun `When observing comments, fetch images and show UIState`(
        @Random comments: List<Comment>,
        @Random imageId: Int,
    ) {
        //given
        every { imageUIModel.id } returns imageId
        coEvery { fetchComments(imageId) } returns comments

        //when
        val resultState = detailImageViewModel.imageDetailState.test()
        val result = detailImageViewModel.comments.test()

        //then
        result.assertValue(comments.toUIModels())
        resultState.assertValueHistory(UIState.NORMAL, UIState.LOADING, UIState.NORMAL)
    }

    @Test
    fun `When observing comments fails, the UIState should be error`(
        @Random imageId: Int,
    ) {
        //given
        val error = Exception()
        every { imageUIModel.id } returns imageId
        coEvery { fetchComments(imageId) } throws error

        //when
        val resultState = detailImageViewModel.imageDetailState.test()
        detailImageViewModel.comments.test()

        //then
        resultState.assertValueHistory(UIState.NORMAL, UIState.LOADING, UIState.ERROR)
    }

    @Test
    fun `When refresh is clicked, fetch comments and show UIState`(
        @Random comments: List<Comment>,
        @Random imageId: Int
    ) {
        //given
        every { imageUIModel.id } returns imageId
        coEvery { fetchComments(imageId) } returns comments

        //when
        val result = detailImageViewModel.comments.test()
        result.assertValue(comments.toUIModels())
        val resultState = detailImageViewModel.imageDetailState.test()
        detailImageViewModel.onRefreshClicked()

        //then
        result.assertValue(comments.toUIModels())
        resultState.assertValueHistory(UIState.NORMAL, UIState.LOADING, UIState.NORMAL)
    }

    @Test
    fun `When refresh fails, show error state`(
        @Random imageId: Int,
    ) {
        // given
        every { imageUIModel.id } returns imageId
        coEvery { fetchComments(imageId) } throws Throwable()

        // when
        val result = detailImageViewModel.imageDetailState.test()
        detailImageViewModel.onRefreshClicked()

        // then
        result.assertValue(UIState.ERROR)
    }
}