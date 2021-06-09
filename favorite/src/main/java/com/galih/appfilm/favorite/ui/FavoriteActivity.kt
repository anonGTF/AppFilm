package com.galih.appfilm.favorite.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.galih.appfilm.di.FavoriteModuleDependencies
import com.galih.appfilm.detail.DetailActivity
import com.galih.appfilm.favorite.DaggerFavoriteComponent
import com.galih.appfilm.favorite.ViewModelFactory
import com.galih.appfilm.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.getData().observe(this, { allData ->
            binding.tvFavMessage.text = if (allData.isEmpty()) {
                "There is no favorite movie"
            } else {
                "Swipe left/right to remove"
            }
            favoriteAdapter.differ.submitList(allData)
        })

        val itemTouchHelperCallbackMovie =
            ItemTouchHelperCallback(favoriteAdapter, binding.root, viewModel)

        ItemTouchHelper(itemTouchHelperCallbackMovie).apply {
            attachToRecyclerView(binding.rvFavMovies)
        }
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        with(binding.rvFavMovies) {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        favoriteAdapter.setOnItemClickListener {
            val intent = Intent(binding.root.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            binding.root.context.startActivity(intent)
        }
    }
}