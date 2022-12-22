package com.llyods.assignment.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


fun View.gone() {
    this.visibility = GONE
}

fun Fragment.snackBar(message: String) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun View.loadImage( url: String?) {
    url?.let {
        Glide.with(this.context).load(it).into(this as ImageView)
    }
}

fun View.showHideProgressBar(isLoading: Boolean = false) {
    this.visibility =
        if (isLoading) VISIBLE else GONE
}