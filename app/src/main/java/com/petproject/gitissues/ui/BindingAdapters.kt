package com.petproject.gitissues.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.petproject.gitissues.R

object BindingAdapters {
    @BindingAdapter("profileImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().circleCrop())
            .placeholder(R.drawable.placeholder)
            .into(view)
    }
}