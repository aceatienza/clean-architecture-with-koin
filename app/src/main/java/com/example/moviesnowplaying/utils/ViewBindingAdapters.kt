package com.example.moviesnowplaying.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("android:loadImage")
fun ImageView.loadImage(url: String?) = Glide.with(context).load(url).into(this)
