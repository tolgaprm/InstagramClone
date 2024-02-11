package com.prmto.share_data.remote.mapper

import com.google.common.truth.Truth.assertThat
import com.prmto.share_data.remote.model.PostDto
import com.prmto.share_domain.model.Post
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class PostMapperTest {

    @Test
    fun `test toPostDto()`() {
        val post = Post(
            id = "id",
            caption = "caption",
            imageUrls = listOf("url1", "url2"),
            date = LocalDateTime.now(),
            userId = "userId"
        )

        val postDto = post.toPostDto()

        assertThat(postDto.id).isEqualTo(post.id)
        assertThat(postDto.caption).isEqualTo(post.caption)
        assertThat(postDto.imageUrls).isEqualTo(post.imageUrls)
        assertThat(postDto.timestamp).isEqualTo(post.date.toEpochSecond(ZoneOffset.UTC))
        assertThat(postDto.userId).isEqualTo(post.userId)
    }

    @Test
    fun `test toPost()`() {
        val postDto = PostDto(
            id = "id",
            caption = "caption",
            imageUrls = listOf("url1", "url2"),
            timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            userId = "userId"
        )

        val post = postDto.toPost()

        assertThat(post.id).isEqualTo(postDto.id)
        assertThat(post.caption).isEqualTo(postDto.caption)
        assertThat(post.imageUrls).isEqualTo(postDto.imageUrls)
        assertThat(post.date).isEqualTo(
            LocalDateTime.ofEpochSecond(
                postDto.timestamp,
                0,
                ZoneOffset.UTC
            )
        )
        assertThat(post.userId).isEqualTo(postDto.userId)
    }
}