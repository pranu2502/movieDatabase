package com.android.example.moviesuggester.dashboard


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.moviesuggester.databinding.MovieItemViewBinding
import com.android.example.moviesuggester.network.MovieDetails

class PhotoGridAdapter( private val onClickListener: OnClickListener ) :
    ListAdapter<MovieDetails,
            PhotoGridAdapter.MovieDetailViewHolder>(DiffCallback) {

    class MovieDetailViewHolder(private var binding: MovieItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieDetails: MovieDetails) {
            binding.detail = movieDetails
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieDetails>() {
        override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieDetailViewHolder {
        return MovieDetailViewHolder(MovieItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MovieDetailViewHolder, position: Int) {
        val movieDetails = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movieDetails)
        }
        holder.bind(movieDetails)
    }

    class OnClickListener(val clickListener: (movieDetails: MovieDetails) -> Unit) {
        fun onClick(movieDetails: MovieDetails) = clickListener(movieDetails)
    }
}
