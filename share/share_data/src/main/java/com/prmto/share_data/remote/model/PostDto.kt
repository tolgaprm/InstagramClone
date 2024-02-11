package com.prmto.share_data.remote.model

data class PostDto(
    val id: String,
    val caption: String,
    val imageUrls: List<String>,
    val timestamp: Long,
    val userId: String
)