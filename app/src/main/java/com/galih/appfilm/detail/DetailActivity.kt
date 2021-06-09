package com.galih.appfilm.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.galih.appfilm.databinding.ActivityDetailBinding
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.utils.IMAGE_URL
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        extras?.let {
            val movie = extras.getParcelable<Movie>(EXTRA_DATA)
            if (movie != null) {
                viewModel.setItem(movie)
                viewModel.item.observe(this, {
                    populateItem(it)
                })
            }
            binding.fab.setOnClickListener {
                if (movie != null) {
                    viewModel.saveMovie(movie)
                }
                Snackbar.make(binding.root, "Program has been added to fav list", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateItem(item: Movie) {
        with(binding) {
            tvTitle.text = item.title
            tvDate.text = item.release_date
            tvResume.text = item.overview
            Glide.with(this@DetailActivity)
                .load(IMAGE_URL + item.backdrop_path)
                .into(poster)
        }
    }
}