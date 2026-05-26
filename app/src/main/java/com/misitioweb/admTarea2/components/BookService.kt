package com.misitioweb.admTarea2.components

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BookService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val query = intent?.getStringExtra("query")
        if (query != null) {
            Log.d("BookService", "Searching for: $query")
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
