package com.galih.appfilm.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Movie(
    val id: Int,
    val title: String?,
    val release_date: String?,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String?
) : Parcelable