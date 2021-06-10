package com.galih.appfilm.core.data.remote

import androidx.annotation.Keep
import com.galih.appfilm.core.domain.model.Movie

@Keep
data class MovieResponse(
    val results: List<Movie>,
    val page: Int? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)