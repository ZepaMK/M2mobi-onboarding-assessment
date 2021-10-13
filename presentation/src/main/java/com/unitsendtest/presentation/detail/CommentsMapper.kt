package com.unitsendtest.presentation.detail

import com.unitsendtest.domain.model.Comment
import com.unitsendtest.presentation.detail.model.CommentUIModel

object CommentsMapper {

    fun List<Comment>.toUIModels(): List<CommentUIModel>  {
        return map { it.toUIModel() }
    }

    fun Comment.toUIModel(): CommentUIModel {
        return CommentUIModel(this, postId, id, name, email, body)
    }
}