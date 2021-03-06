package com.galih.appfilm.favorite.ui

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class ItemTouchHelperCallback(
        private val adapter: FavoriteAdapter,
        private val view: View,
        private val viewModel: FavoriteViewModel
) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val movie = adapter.differ.currentList[position]
        viewModel.deleteMovie(movie)
        Snackbar.make(view, "Successfully deleted program", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.saveMovie(movie)
            }
            show()
        }
    }
}