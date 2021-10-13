package com.unitsendtest.m2mobiandroidassessment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unitsendtest.presentation.detail.DetailImageViewModel
import com.unitsendtest.presentation.main.model.ImageUIModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface DetailModule {

    fun create(imageUIModel: ImageUIModel): DetailImageViewModel

    companion object {

        fun provideFactory(
            detailModule: DetailModule,
            imageUIModel: ImageUIModel
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return detailModule.create(imageUIModel) as T
            }
        }
    }
}