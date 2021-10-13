package com.unitsendtest.m2mobiandroidassessment.main

import com.airbnb.epoxy.TypedEpoxyController
import com.unitsendtest.m2mobiandroidassessment.itemHeader
import com.unitsendtest.m2mobiandroidassessment.itemImage
import com.unitsendtest.presentation.main.model.ImageSection
import com.unitsendtest.presentation.main.model.ImageUIModel

class ImagesController(
    var onImage: ((ImageUIModel) -> Unit)? = null,
) : TypedEpoxyController<List<ImageSection>>() {

    override fun buildModels(data: List<ImageSection>) {
        for (item in data) {
            bindHeader(item.header)
            bindImages(item.images)
        }
    }

    private fun bindHeader(header: String) {
        itemHeader {
            id(header)
            imageHeader(header)
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    private fun bindImages(images: List<ImageUIModel>) {
        for (image in images) {
            bindImage(image)
        }
    }

    private fun bindImage(item: ImageUIModel) {
        itemImage {
            id(item.id)
            uiModel(item)
            onClick { onImage?.invoke(item) }
        }
    }
}