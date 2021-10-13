package com.unitsendtest.presentation.main.model
import com.unitsendtest.domain.model.Image
import java.io.Serializable

data class ImageUIModel(
    internal val image: Image,
    val albumId: Int,
    val id: Int,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String,
) : Serializable