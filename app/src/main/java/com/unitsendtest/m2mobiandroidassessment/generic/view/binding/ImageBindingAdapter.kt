package com.unitsendtest.m2mobiandroidassessment.generic.view.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:src")
internal fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(this)
            .load(it)
            .into(this)
    }
}

