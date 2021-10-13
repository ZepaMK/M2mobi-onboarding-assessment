package com.unitsendtest.presentation.detail.model

import com.unitsendtest.domain.model.Comment
import java.io.Serializable

data class CommentUIModel(
    internal val comment: Comment,
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
) : Serializable
