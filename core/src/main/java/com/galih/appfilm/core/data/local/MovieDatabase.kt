package com.galih.appfilm.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.galih.appfilm.core.data.local.dao.FavoriteMovieDao
import com.galih.appfilm.core.data.local.dao.MovieDao
import com.galih.appfilm.core.data.local.entities.FavoriteMovieEntity
import com.galih.appfilm.core.data.local.entities.MovieEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [MovieEntity::class, FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getFavMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()
        private val passphrase: ByteArray = SQLiteDatabase.getBytes("galih".toCharArray())
        val factory = SupportFactory(passphrase)

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie_db.db"
            )
                .fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
    }
}