package com.unitsendtest.data.image.mapper

import com.m2mobi.utility.test.EasyRandomExtension
import com.m2mobi.utility.test.Random
import com.m2mobi.utility.test.assertEquals
import com.unitsendtest.data.image.mapper.CommentMapper.toComment
import com.unitsendtest.data.image.network.response.CommentResponse
import com.unitsendtest.domain.model.Comment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EasyRandomExtension::class)
internal class CommentMapperTest {

    @Test
    fun `CommentResponse to Comment mapping`(
        @Random commentResponse: CommentResponse
    ) {
        //when
        val result = commentResponse.toComment()

        //then
        result assertEquals Comment(
            postId = commentResponse.postId,
            id = commentResponse.id,
            name = commentResponse.name,
            email = commentResponse.email,
            body = commentResponse.body,
        )
    }
}