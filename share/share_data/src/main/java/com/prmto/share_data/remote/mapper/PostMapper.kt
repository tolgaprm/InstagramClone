package com.prmto.share_data.remote.mapper

import com.prmto.share_data.remote.model.PostDto
import com.prmto.share_domain.model.Post
import java.time.LocalDateTime
import java.time.ZoneOffset

fun Post.toPostDto(): PostDto {
    return PostDto(
        id = id,
        caption = caption,
        imageUrls = imageUrls,
        timestamp = date.toEpochSecond(ZoneOffset.UTC),
        userId = userId
    )
}

fun PostDto.toPost(): Post {
    return Post(
        id = id,
        caption = caption,
        imageUrls = imageUrls,
        date = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC),
        userId = userId
    )
}