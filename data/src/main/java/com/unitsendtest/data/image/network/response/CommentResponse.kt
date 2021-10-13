package com.unitsendtest.data.image.network.response

import com.google.gson.annotations.SerializedName
import com.unitsendtest.domain.model.Comment
import com.unitsendtest.domain.model.Image

data class CommentResponse(

    @SerializedName("postId")
    val postId: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("body")
    val body: String,
)