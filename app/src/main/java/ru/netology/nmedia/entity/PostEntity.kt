package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val likes: Int,
    val shares: Int,
    val views:Int,
    val video: String? = null,
) {
    fun toDto() =
        Post(id, author, published, content, likedByMe, likes, shares, views, video)

    companion object {
        fun fromDto(post: Post) = with(post) {
            PostEntity(id, author, published, content, likedByMe, likes, shares, views, video)
        }
    }
}