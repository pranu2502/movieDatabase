package com.android.example.moviesuggester

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.moviesuggester.dashboard.PhotoGridAdapter
import com.android.example.moviesuggester.network.MovieDetails
import com.bumptech.glide.Glide


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MovieDetails>?) {

    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }

}

