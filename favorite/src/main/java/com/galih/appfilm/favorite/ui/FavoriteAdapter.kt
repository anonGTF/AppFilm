package com.galih.appfilm.favorite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.utils.IMAGE_URL
import com.galih.appfilm.favorite.databinding.ItemFavoriteBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.MovieViewHolder>() {

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldMovie: Movie, newMovie: Movie) =
                oldMovie == newMovie
        }
    }

    inner class MovieViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]

        holder.binding.tvJudulFav.text = truncateString(movie.title, 24)

        holder.binding.tvOverviewFav.text = truncateString(movie.overview, 30)

        Glide.with(holder.itemView.context)
            .load(IMAGE_URL + movie.poster_path)
            .apply(RequestOptions().override(180,270))
            .into(holder.binding.imageFav)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    private fun truncateString(string: String?, maxSize: Int): String? {
        if (string != null) {
            if (string.length < maxSize) return string
            return string.take(maxSize) + "..."
        } else {
            return null
        }
    }
}