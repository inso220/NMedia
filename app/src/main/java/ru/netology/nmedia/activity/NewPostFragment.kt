package ru.netology.nmedia.activity

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by activityViewModels()


        arguments?.let{
            val text = it.getString("textArg", null)
            binding.content.setText(text)
        }

        binding.ok.setOnClickListener {

            if (!binding.content.text.isNullOrBlank()) {
                val text = binding.content.text.toString()
                viewModel.changeContent(text)
                viewModel.save()
            }
            findNavController().navigateUp()
        }

        return binding.root
    }
}