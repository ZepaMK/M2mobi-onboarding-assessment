package com.unitsendtest.domain

import com.m2mobi.utility.test.CoroutinesExtension
import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.domain.data.ImageRepository
import com.unitsendtest.domain.model.Comment
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class, CoroutinesExtension::class, EasyRandomExtension::class)
internal class FetchCommentsTest {

    @RelaxedMockK
    lateinit var imageRepository: ImageRepository

    @InjectMockKs
    lateinit var fetchComments: FetchComments

    @Test
    fun `When invoked, return a list of comments from the repository`(
        @Random comments: List<Comment>,
        @Random id: Int,
    ) = runBlocking {
        //given
        coEvery { imageRepository.fetchComments(id) } returns comments

        //when
        val result = fetchComments(id)

        //then
        result assertEquals comments.sortedBy { it.id }
    }
}