package com.unitsendtest.m2mobiandroidassessment.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unitsendtest.m2mobiandroidassessment.R
import com.unitsendtest.m2mobiandroidassessment.databinding.ActivityMainBinding
import com.unitsendtest.m2mobiandroidassessment.detail.DetailActivity
import com.unitsendtest.m2mobiandroidassessment.generic.createErrorSnackbar
import com.unitsendtest.presentation.generic.UIState
import com.unitsendtest.presentation.main.ImageListViewModel
import com.unitsendtest.presentation.main.model.ImageListNavAction
import com.unitsendtest.presentation.main.model.ImageListNavAction.OpenImageDetails
import com.unitsendtest.presentation.main.model.ImageSection
import com.unitsendtest.presentation.main.model.ImageUIModel
import dagger.hilt.android.AndroidEntryPoint
import java.security.AccessController.getContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding

    private val viewModel: ImageListViewModel by viewModels()

    private val controller by lazy { ImagesController(viewModel::onImageClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        initViews()
        observeViewModel()
    }

    private fun initViews() = ui.run {
        rvImages.layoutManager =
            GridLayoutManager(applicationContext, SPAN_COUNT, RecyclerView.VERTICAL, false)
        controller.spanCount = SPAN_COUNT
        rvImages.setController(controller)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.onRefreshClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.images.observe(this, ::bindImages)
        viewModel.imagesState.observe(this, ::bindImagesState)
        viewModel.navigation.observe(this, ::handleNavAction)
    }

    private fun handleNavAction(action: ImageListNavAction) = when (action) {
        is OpenImageDetails -> openImageDetails(action.image)
    }

    private fun bindImages(imageList: List<ImageSection>) {
        controller.setData(imageList)
    }

    private fun bindImagesState(uiState: UIState) = ui.run {
        when (uiState) {
            UIState.LOADING -> loader.visibility = View.VISIBLE
            UIState.ERROR -> {
                root.createErrorSnackbar(R.string.error_message_images).show()
                loader.visibility = View.GONE
            }
            UIState.NORMAL -> loader.visibility = View.GONE
        }
    }

    private fun openImageDetails(image: ImageUIModel) {
        startActivity(DetailActivity.createIntent(this, image))
    }

    companion object {

        private const val SPAN_COUNT = 2

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

