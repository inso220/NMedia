package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getData() = dao.getData().map { list ->
        list.map { it.toDto() }
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)

    }

    override fun shareById(id: Long) {
        dao.shareById(id)

    }

    override fun removeById(id: Long) {
        dao.removeById(id)

    }
}

