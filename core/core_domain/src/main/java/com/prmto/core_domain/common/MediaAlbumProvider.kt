package com.prmto.core_domain.common

import android.net.Uri

interface MediaAlbumProvider {
    suspend fun getAllAlbumNames(): Set<String>

    suspend fun getAllUrisForAlbum(albumName: String): Set<Uri>

    suspend fun getLastUriOfTheImage(): Uri

    suspend fun getFirstImageUriOfTheAlbum(albumName: String): Uri
}