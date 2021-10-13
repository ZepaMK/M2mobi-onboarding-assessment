package com.unitsendtest.m2mobiandroidassessment.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.unitsendtest.m2mobiandroidassessment.R
import com.unitsendtest.m2mobiandroidassessment.databinding.ActivityDetailBinding
import com.unitsendtest.m2mobiandroidassessment.generic.createErrorSnackbar
import com.unitsendtest.presentation.detail.DetailImageViewModel
import com.unitsendtest.presentation.detail.model.CommentUIModel
import com.unitsendtest.presentation.generic.UIState
import com.unitsendtest.presentation.main.model.ImageUIModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var ui: ActivityDetailBinding

    @Inject
    lateinit var detailModule: DetailModule

    private val viewModel: DetailImageViewModel by viewModels {
        DetailModule.provideFactory(detailModule, intent.getSerializableExtra(DETAIL_IMAGE) as ImageUIModel)
    }

    private val controller by lazy { CommentsController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(ui.root)

        initViews()
        observeViewModel()
    }

    private fun initViews() = ui.run {
        rvComments.layoutManager =
            LinearLayoutManager(this@DetailActivity, RecyclerView.VERTICAL, false)
        rvComments.setController(controller)

        swipeRefreshLayoutDetail.setOnRefreshListener {
            swipeRefreshLayoutDetail.isRefreshing = false
            viewModel.onRefreshClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.image.observe(this, ::bindImage)
        viewModel.imageDetailState.observe(this, ::bindImageDetailState)
        viewModel.comments.observe(this, ::bindComments)
    }

    private fun bindImage(imageUIModel: ImageUIModel) = ui.run {
        detailTitle.text = imageUIModel.title

        val url = GlideUrl(imageUIModel.imageUrl, LazyHeaders.Builder()
            .addHeader("User-Agent", "M2Assessment")
            .build())

        Glide.with(this@DetailActivity)
            .load(url)
            .into(detailImage)
    }

    private fun bindComments(commentList: List<CommentUIModel>) {
        controller.setData(commentList)
    }

    private fun bindImageDetailState(uiState: UIState) = ui.run {
        when (uiState) {
            UIState.LOADING -> loaderDetail.visibility = View.VISIBLE
            UIState.ERROR -> {
                root.createErrorSnackbar(R.string.error_message_comments).show()
                loaderDetail.visibility = View.GONE
            }
            UIState.NORMAL -> loaderDetail.visibility = View.GONE
        }
    }

    companion object {

        internal const val DETAIL_IMAGE = "DETAIL_IMAGE"

        fun createIntent(context: Context, image: ImageUIModel): Intent {
            return Intent(context, DetailActivity::class.java).putExtra(DETAIL_IMAGE, image)
        }
    }
}




