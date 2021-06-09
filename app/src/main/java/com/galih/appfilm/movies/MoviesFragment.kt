package com.galih.appfilm.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.galih.appfilm.databinding.FragmentMoviesBinding
import com.galih.appfilm.detail.DetailActivity
import com.galih.appfilm.core.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView()

            viewModel.getData().observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        hideErrorMessage()
                        movieAdapter.differ.submitList(response.data)
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(binding.root.context, "An error occured: $message", Toast.LENGTH_LONG).show()
                            showErrorMessage(message)
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        }
    }

    private fun showErrorMessage(message: String) {
        binding.error.cvError.visibility = View.VISIBLE
        binding.error.tvErrorMessage.text = message
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.error.cvError.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }

        movieAdapter.setOnItemClickListener {
            val intent = Intent(binding.root.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            binding.root.context.startActivity(intent)
        }
    }
}