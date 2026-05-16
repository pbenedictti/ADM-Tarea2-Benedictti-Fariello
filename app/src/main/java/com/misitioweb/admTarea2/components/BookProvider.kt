package com.misitioweb.admTarea2.components

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.misitioweb.admTarea2.data.local.BookDatabase

class BookProvider : ContentProvider() {

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val context = context ?: return null
        val db = BookDatabase.getDatabase(context)
        // Simplified: return all favorites as a cursor
        return db.query("SELECT * FROM favorite_books", null)
    }

    override fun getType(uri: Uri): String? = "vnd.android.cursor.dir/vnd.com.misitioweb.admTarea2.favorites"

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
}
