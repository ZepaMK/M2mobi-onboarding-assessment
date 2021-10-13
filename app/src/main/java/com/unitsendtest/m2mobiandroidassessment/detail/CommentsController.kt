package com.unitsendtest.m2mobiandroidassessment.detail

import com.airbnb.epoxy.TypedEpoxyController
import com.unitsendtest.m2mobiandroidassessment.itemComment
import com.unitsendtest.presentation.detail.model.CommentUIModel

class CommentsController : TypedEpoxyController<List<CommentUIModel>>() {

    override fun buildModels(data: List<CommentUIModel>) {
        for (item in data) {
            itemComment {
                id(item.id)
                uiModel(item)
            }
        }
    }
}