package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.Functions

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeClicked: OnLikeClickListener,
    private val onShareClicked: OnShareClickListener,
) : ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {

            author.text = post.author
            content.text = post.content
            published.text = post.published

            viewsCount.text = Functions.thousandsAndMillionsChanger(post.views)
            shareCount.text = Functions.thousandsAndMillionsChanger(post.shares)

            if (post.likedByMe) {
                likes.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                likes.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            likes.setOnClickListener {
                onLikeClicked(post)
            }

            share.setOnClickListener {
                onShareClicked(post)
            }

            likesCount.text = Functions.thousandsAndMillionsChanger(post.likes)
        }
    }
}

