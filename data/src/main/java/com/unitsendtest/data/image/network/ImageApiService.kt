package com.unitsendtest.data.image.network

import com.unitsendtest.data.image.network.response.CommentResponse
import com.unitsendtest.data.image.network.response.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApiService {

    @GET("photos")
    suspend fun getImages(): List<ImageResponse>

    @GET("photos/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): List<CommentResponse>
}