package com.llyods.assignment.extensions

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale


fun View.gone() {
    this.visibility = View.GONE
}

fun Fragment.snackBar(message: String) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun View.click(it: (View) -> Unit) {
    this.setOnClickListener(it)
}

fun TextView.dateFormatter(string: String?) {
    if (string?.isNotEmpty() == true) {
        val date = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(string)
        date?.let {
            val format = SimpleDateFormat("dd/MM/yyy", Locale.getDefault())
            this.text = format.format(it)
        }

    }
}

// show image using Glide
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).into(view)
    }
}