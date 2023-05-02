package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
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
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            }
        )

        binding.add.setOnClickListener{
            newPostContract.launch()
        }

//        viewModel.edited.observe(this) {
//            if (it.id == 0L) {
//                binding.clearedit.visibility = View.GONE
//                return@observe
//            }
//            binding.clearedit.visibility = View.VISIBLE
//            binding.content.requestFocus()
//            binding.content.setText(it.content)
//        }
//
//        binding.clearedit.setOnClickListener {
//            with(binding.content) {
//                viewModel.clearEdit()
//                setText("")
//                clearFocus()
//                Functions.hideKeyboard(this)
//            }
//        }
//
//        binding.save.setOnClickListener {
//            with(binding.content) {
//                val content = text.toString()
//                if (content.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        R.string.epmpty_content_error,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//                viewModel.changeContent(content)
//                viewModel.save()
//
//                setText("")
//                clearFocus()
//                Functions.hideKeyboard(this)
//            }
//        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.list.adapter = adapter
    }

}



