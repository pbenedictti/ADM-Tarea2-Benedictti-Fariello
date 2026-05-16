package com.misitioweb.admTarea2.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class FavoriteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("book_title")
        if (title != null) {
            Toast.makeText(context, "Libro agregado a favoritos: $title", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ACTION_FAVORITE_ADDED = "com.misitioweb.admTarea2.ACTION_FAVORITE_ADDED"
    }
}
