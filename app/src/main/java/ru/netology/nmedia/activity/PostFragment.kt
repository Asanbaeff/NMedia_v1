package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.getValue

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSinglePostBinding.inflate(inflater, container, false)
        val content = arguments?.getString("textArg")


        binding.post.content.text = content
        binding.post.like.text = "like"
        binding.post.author.text = "author"
        binding.post.published.text = "published"
        binding.post.share.text = "shares"

        binding.post.like.setOnClickListener {
            binding.post.like.text = "99"
        }
        binding.post.share.setOnClickListener {
            binding.post.share.text = "Sh"
        }
        //binding.post.video.setOnClickListener {}

        binding.post.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            binding.post.content.text = "remove"
                            true
                        }

                        R.id.edit -> {
                            binding.post.content.text = "edit"
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
        return binding.root
    }
}