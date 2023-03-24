package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val post = Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )

        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published

            if (post.likedByMe) {
                likes.setImageResource(R.drawable.baseline_favorite_24)
            }

            likesCount.text = ThousandsAndMillionsUpdate(post.likes)

            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                post.likes = if (post.likedByMe) {
                    post.likes + 1
                } else {
                    post.likes - 1
                }

                likesCount.text = ThousandsAndMillionsUpdate(post.likes)

                likes.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.baseline_favorite_24
                    } else {
                        R.drawable.baseline_favorite_border_24
                    }
                )
            }

            shareCount.text = ThousandsAndMillionsUpdate(post.shares)
            share.setOnClickListener {
                post.shares = post.shares + 1
                shareCount.text = ThousandsAndMillionsUpdate(post.shares)
            }

            viewsCount.text = ThousandsAndMillionsUpdate(post.views)
        }

    }
}


fun ThousandsAndMillionsUpdate(count: Int): String {
    return when (count) {
        in 0..999 -> count.toString()
        in 1000..9999 -> (count / 1000).toString() +
                if (count / 100 - count / 1000 * 10 > 0) {
                    "." + (count / 100 - count / 1000 * 10).toString() + "K"
                } else {
                    "K"
                }
        in 10000..999999 -> (count / 1000).toString() + "K"
        else -> (count / 1000000).toString() +
                if (count / 100000 - count / 1000000 * 10 > 0) {
                    "." + (count / 100000 - count / 1000000 * 10).toString() + "M"
                } else {
                    "M"
                }
    }
}
