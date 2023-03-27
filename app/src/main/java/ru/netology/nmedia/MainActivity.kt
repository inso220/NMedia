package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Functions
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
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

                likesCount.text = Functions.thousandsAndMillionsChanger(post.likes)
            }
        }

        binding.likes.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener {
            viewModel.share()
        }


    }
}



