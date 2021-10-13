package com.unitsendtest.presentation.main.model

sealed class ImageListNavAction {

    data class OpenImageDetails(val image: ImageUIModel) : ImageListNavAction()
}
