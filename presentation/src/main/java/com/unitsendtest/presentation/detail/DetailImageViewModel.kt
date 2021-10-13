package com.unitsendtest.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitsendtest.domain.FetchComments
import com.unitsendtest.presentation.detail.CommentsMapper.toUIModels
import com.unitsendtest.presentation.detail.model.CommentUIModel
import com.unitsendtest.presentation.generic.UIState
import com.unitsendtest.presentation.generic.launchOnIO
import com.unitsendtest.presentation.main.model.ImageUIModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler

class DetailImageViewModel @AssistedInject constructor(
    private val fetchComments: FetchComments,
    @Assisted private val imageUIModel: ImageUIModel,
) : ViewModel() {

    private val _image = MutableLiveData(imageUIModel)
    val image: LiveData<ImageUIModel> = _image

    private val _comments = MutableLiveData<List<CommentUIModel>>()
    val comments: LiveData<List<CommentUIModel>> by lazy {
        getComments()
        _comments
    }

    private val _imageDetailState = MutableLiveData(UIState.NORMAL)
    val imageDetailState: LiveData<UIState> = _imageDetailState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Handle $exception in CoroutineExceptionHandler")
        _imageDetailState.postValue(UIState.ERROR)
    }

    private fun getComments() {
        viewModelScope.launchOnIO(coroutineExceptionHandler) {
            _imageDetailState.postValue(UIState.LOADING)
            _comments.postValue(fetchComments.invoke(imageUIModel.id).toUIModels())
            _imageDetailState.postValue(UIState.NORMAL)
        }
    }

    fun onRefreshClicked() {
        getComments()
    }
}