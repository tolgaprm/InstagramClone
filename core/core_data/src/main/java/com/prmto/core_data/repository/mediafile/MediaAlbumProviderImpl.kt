package com.prmto.core_data.repository.mediafile

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_domain.repository.mediafile.MediaAlbumProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaAlbumProviderImpl @Inject constructor(
    private val contentResolver: ContentResolver,
    private val dispatcherProvider: DispatcherProvider
) : MediaAlbumProvider {

    override suspend fun getAllAlbumNames(): Set<String> {
        val albumsNames: MutableSet<String> = mutableSetOf()
        return withContext(dispatcherProvider.IO) {
            // This is the array of columns we want to fetch
            val projection = arrayOf(
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            // Create the arguments for the query for sorting by `BUCKET_DISPLAY_NAME`
            val queryArgs = Bundle().apply {
                putString(
                    ContentResolver.QUERY_ARG_SQL_SORT_ORDER,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                )
            }

            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                queryArgs,
                null
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val bucketNameIndex =
                        cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                    val albumName = cursor.getString(bucketNameIndex)
                    albumsNames.add(albumName)
                }
                cursor.close()
            }
            albumsNames
        }
    }

    override suspend fun getAllUrisForAlbum(albumName: String): Set<Uri> {
        return withContext(dispatcherProvider.IO) {
            val uris: MutableSet<Uri> = mutableSetOf()
            val cursor = setCursorToGetUrisByAlbumName(albumName)
            cursor?.use {
                while (cursor.moveToNext()) {
                    uris.add(it.getUriFromCursor())
                }
            }
            uris
        }
    }

    override suspend fun getLastUriOfTheImage(): Uri {
        var uri: Uri? = null
        return withContext(dispatcherProvider.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_ADDED
            )

            val queryArgs = Bundle().apply {
                putString(
                    ContentResolver.QUERY_ARG_SQL_SORT_ORDER,
                    MediaStore.Images.Media.DATE_ADDED
                )
            }

            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                queryArgs,
                null
            )
            cursor?.let {
                if (cursor.moveToNext()) {
                    cursor.use {
                        uri = it.getUriFromCursor()
                    }
                }
            }
            uri ?: Uri.EMPTY
        }
    }

    override suspend fun getFirstImageUriOfTheAlbum(albumName: String): Uri {
        return withContext(dispatcherProvider.IO) {
            var uri: Uri? = null
            val cursor = setCursorToGetUrisByAlbumName(albumName)
            cursor?.use {
                if (cursor.moveToNext()) {
                    uri = it.getUriFromCursor()
                }
            }
            uri ?: Uri.EMPTY
        }
    }

    private fun setCursorToGetUrisByAlbumName(albumName: String): Cursor? {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA
        )
        val selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?"
        return contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            arrayOf(albumName),
            null
        )
    }

    private fun Cursor.getUriFromCursor(): Uri {
        val uriIndex = this.getColumnIndex(MediaStore.Images.Media._ID)
        return Uri.withAppendedPath(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            this.getString(uriIndex)
        )
    }
}