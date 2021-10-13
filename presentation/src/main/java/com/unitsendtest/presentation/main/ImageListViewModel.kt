package com.unitsendtest.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitsendtest.domain.FetchImages
import com.unitsendtest.presentation.generic.SingleLiveEvent
import com.unitsendtest.presentation.generic.UIState
import com.unitsendtest.presentation.generic.launchOnIO
import com.unitsendtest.presentation.main.ImagesMapper.toSections
import com.unitsendtest.presentation.main.model.ImageListNavAction
import com.unitsendtest.presentation.main.model.ImageListNavAction.OpenImageDetails
import com.unitsendtest.presentation.main.model.ImageSection
import com.unitsendtest.presentation.main.model.ImageUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val fetchImages: FetchImages,
) : ViewModel() {

    private val _images = MutableLiveData<List<ImageSection>>()
    val images: LiveData<List<ImageSection>> by lazy {
        getImages()
        _images
    }

    private val _imagesState = MutableLiveData(UIState.NORMAL)
    val imagesState: LiveData<UIState> = _imagesState

    private val _navigation: SingleLiveEvent<ImageListNavAction> = SingleLiveEvent()
    val navigation: LiveData<ImageListNavAction> = _navigation

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Handle $exception in CoroutineExceptionHandler")
        _imagesState.postValue(UIState.ERROR)
    }

    private fun getImages() {
        viewModelScope.launchOnIO(coroutineExceptionHandler) {
            _imagesState.postValue(UIState.LOADING)
            _images.postValue(fetchImages().toSections())
            _imagesState.postValue(UIState.NORMAL)
        }
    }

    fun onRefreshClicked() {
        getImages()
    }

    fun onImageClicked(image: ImageUIModel) {
        _navigation.postValue(OpenImageDetails(image))
    }
}