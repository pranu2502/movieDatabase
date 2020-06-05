package com.android.example.moviesuggester

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.moviesuggester.dashboard.MovieApiStatus
import com.android.example.moviesuggester.dashboard.PhotoGridAdapter
import com.android.example.moviesuggester.dashboard.SearchStatus
import com.android.example.moviesuggester.network.MovieDetails
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


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
            .apply(
                RequestOptions()
                .error(R.drawable.unavailable))
            .into(imgView)
    }

}

@BindingAdapter("movieApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: MovieApiStatus?) {

    when (status) {
        MovieApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        MovieApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.internet)
        }

        MovieApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }

    }
}

@BindingAdapter("movieSearchStatus")
fun bindSearch(statusImageView: ImageView,status:SearchStatus?){
    if(status == MovieApiStatus.ERROR)
    {
        statusImageView.visibility = View.GONE
    }
    else {
        when (status) {
            SearchStatus.NOT -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.non_search)
            }

            SearchStatus.SEARCHED -> {
                statusImageView.visibility = View.GONE
            }
        }
    }
}



