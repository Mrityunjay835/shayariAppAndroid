package com.pack.shayariapp

import android.app.AlertDialog
import android.content.Context

class DeleteConfirmationDialog(private val context: Context) {
    fun showDeleteDialog(
        shayari: NewDataEntity,
        position: Int,
        deleteItemListener: RvAdapter.OnDeleteItemClickListener
    ) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Delete Confirmation")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { _, _ ->
                deleteItemListener.deleteItem(shayari, position)
            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.show()
    }
}
