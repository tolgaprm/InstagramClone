package com.prmto.core_testing.fake_repository.common

import android.net.Uri
import com.prmto.core_domain.common.MediaAlbumProvider

class MediaAlbumProviderFake : MediaAlbumProvider {

    var albumNames: List<String> = emptyList()

    var albumsAndUris = mapOf<String, List<Uri>>()

    lateinit var lastUri: Uri

    override suspend fun getAllAlbumNames(): Set<String> {
        return albumNames.toSet()
    }

    override suspend fun getAllUrisForAlbum(albumName: String): Set<Uri> {
        return albumsAndUris.filter { it.key == albumName }.values.flatten().toSet()
    }

    override suspend fun getLastUriOfTheImage(): Uri {
        return lastUri
    }

    override suspend fun getFirstImageUriOfTheAlbum(albumName: String): Uri {
        TODO("Not yet implemented")
    }
}