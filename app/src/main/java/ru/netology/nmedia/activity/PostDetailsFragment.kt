package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostDetailsBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class PostDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostDetailsBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val viewModel: PostViewModel by activityViewModels()

        arguments?.let { it ->
            val postId = it.getLong("postId", -1)

            viewModel.data.observe(viewLifecycleOwner) { list ->
                list.find { it.id == postId }?.let { post ->

                    PostViewHolder(
                        binding.postDetailsFragment,
                        object : PostListener {
                            override fun onRemove(post: Post) {
                                viewModel.removeById(post.id)
                                findNavController().navigate(
                                    R.id.action_postDetailsFragment_to_feedFragment
                                )
                            }

                            override fun onEdit(post: Post) {
                                viewModel.edit(post)
                                findNavController().navigate(
                                    R.id.action_postDetailsFragment_to_newPostFragment,
                                    bundleOf("textArg" to post.content)
                                )
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
                                    Intent.createChooser(
                                        intent,
                                        getString(R.string.chooser_share_post)
                                    )
                                startActivity(startIntent)
                            }

                            override fun onVideo(post: Post) {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(post.video))

                                startActivity(intent)
                            }

                            override fun onDetailsPost(post: Post) { }

                        }
                    ).bind(post)
                }
            }
        }

        return binding.root
    }
}