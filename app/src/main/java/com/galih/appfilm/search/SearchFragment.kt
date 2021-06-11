package com.galih.appfilm.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.galih.appfilm.databinding.FragmentSearchBinding
import com.galih.appfilm.detail.DetailActivity
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.movies.MovieAdapter
import com.galih.appfilm.core.utils.Resource
import com.galih.appfilm.core.utils.SEARCH_USER_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (activity != null) {
            setupRecyclerView()

            var job: Job? = null
            binding.etSearch.addTextChangedListener { editable ->
                job?.cancel()
                if (editable.toString().isNotEmpty()) {
                    job = MainScope().launch {
                        delay(SEARCH_USER_TIME_DELAY)
                        editable?.let {
                            viewModel.searchMovie(editable.toString())
                        }
                    }
                } else {
                    movieAdapter.differ.submitList(null)
                    showSearchPlaceholder()
                    hideErrorMessage()
                }
            }

            viewModel.data.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        handleSuccess(response)
                    }
                    is Resource.Error -> {
                        handleError(response)
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        }

    }

    private fun handleSuccess(response: Resource.Success<List<Movie>>) {
        hideProgressBar()
        hideErrorMessage()
        hideSearchPlaceholder()
        if (response.data?.size == 0) {
            handleZeroResult()
        } else {
            movieAdapter.differ.submitList(response.data)
        }
    }

    private fun handleZeroResult() {
        showErrorMessage("Sorry there is no such movie with that title")
        showSearchPlaceholder()
        movieAdapter.differ.submitList(null)
    }

    private fun handleError(response: Resource.Error<List<Movie>>) {
        hideProgressBar()
        response.message?.let { message ->
            showErrorMessage(message)
        }
    }

    private fun showSearchPlaceholder() {
        binding.searchPlaceholder.searchPlaceholder.visibility = View.VISIBLE
    }

    private fun showErrorMessage(message: String) {
        binding.error.cvError.visibility = View.VISIBLE
        binding.error.tvErrorMessage.text = message
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideSearchPlaceholder() {
        binding.searchPlaceholder.searchPlaceholder.visibility = View.INVISIBLE
    }

    private fun hideErrorMessage() {
        binding.error.cvError.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        with(binding.rvSearchMovie) {
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