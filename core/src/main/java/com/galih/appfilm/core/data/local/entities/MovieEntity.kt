package com.galih.appfilm.core.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "movies"
)
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String?,
    val release_date: String?,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String?,
) : Parcelable