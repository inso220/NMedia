package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.Functions
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private val newPostContract = registerForActivityResult(NewPostActivity.Contract) { result ->
        result ?: return@registerForActivityResult
        viewModel.changeContent(result)
        viewModel.save()
    }

    private val editPostContract = registerForActivityResult(EditPostActivity.ContractEdit) { result ->
        result ?: return@registerForActivityResult
        viewModel.changeContent(result)
        viewModel.save()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("posts", Context.MODE_PRIVATE)
        prefs.edit().apply{
            putString("key", "test")
            apply()
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val previousResult = prefs.getString("key", null)
//        Snackbar.make(binding.root, previousResult.orEmpty(), Snackbar.LENGTH_LONG).show()



        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    editPostContract.launch(post.content)
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val startIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(startIntent)
                }

                override fun onVideo(post: Post) {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(post.video))

                    startActivity(intent)
                }

            }
        )

        binding.add.setOnClickListener{
            newPostContract.launch()
        }



        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearEdit()
    }

}



