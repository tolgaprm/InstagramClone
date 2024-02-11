package com.prmto.share_domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Post(
    val id: String = UUID.randomUUID().toString(),
    val caption: String,
    val imageUrls: List<String>,
    val date: LocalDateTime = LocalDateTime.now(),
    val userId: String
)