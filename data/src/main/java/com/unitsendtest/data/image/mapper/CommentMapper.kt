package com.unitsendtest.data.image.mapper

import com.unitsendtest.data.image.network.response.CommentResponse
import com.unitsendtest.domain.model.Comment

object CommentMapper {

    fun CommentResponse.toComment(): Comment {
        return Comment(postId, id, name, email, body)
    }
}