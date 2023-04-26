package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.Functions

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener,
) : ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {

            author.text = post.author
            content.text = post.content
            published.text = post.published

            views.text = Functions.thousandsAndMillionsChanger(post.views)
            share.text = Functions.thousandsAndMillionsChanger(post.shares)

            likes.isChecked = post.likedByMe

            likes.setOnClickListener {
                listener.onLike(post)
            }
            likes.text = Functions.thousandsAndMillionsChanger(post.likes)

            share.setOnClickListener {
                listener.onShare(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { item ->
                        when(item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }
                    .show()
            }
        }
    }
}

