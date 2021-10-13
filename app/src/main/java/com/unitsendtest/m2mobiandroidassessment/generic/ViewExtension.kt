package com.unitsendtest.m2mobiandroidassessment.generic

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.unitsendtest.m2mobiandroidassessment.R

fun View.createErrorSnackbar(@StringRes text: Int): Snackbar {
    return createCustomErrorSnackbar(context.getString(text))
}

fun View.createCustomErrorSnackbar(text: String): Snackbar {
    return Snackbar.make(this, text, Snackbar.LENGTH_LONG)
        .apply {
            view.findViewById<TextView>(R.id.snackbar_text).setTextAppearance(R.style.ErrorSnackbar)
        }
}