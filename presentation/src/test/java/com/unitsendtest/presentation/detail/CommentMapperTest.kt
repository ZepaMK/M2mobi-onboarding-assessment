package com.unitsendtest.presentation.detail

import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.domain.model.Comment
import com.unitsendtest.presentation.detail.CommentsMapper.toUIModel
import com.unitsendtest.presentation.detail.CommentsMapper.toUIModels
import com.unitsendtest.presentation.detail.model.CommentUIModel
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EasyRandomExtension::class)
class CommentMapperTest {

    @Test
    fun `Comment to CommentUIModel mapping`(
        @Random comment: Comment
    ) {
        //when
        val result = comment.toUIModel()

        //then
        result assertEquals CommentUIModel(
            comment = comment,
            postId = comment.postId,
            id = comment.id,
            name = comment.name,
            email = comment.email,
            body = comment.body,
        )
    }

    @Test
    fun `List of Comment's to list of CommentUIModel's mapping`(
        @Random comments: List<Comment>
    ) {
        //when
        val result = comments.toUIModels()

        //then
        result assertEquals comments.map { it.toUIModel() }
    }
}