package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSinglePostBinding.inflate(
            inflater, container, false
        )
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


        val postId = arguments?.getLong("postId")

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe

            binding.post.like.setOnClickListener {
                viewModel.likeById(post.id)
            }

            binding.post.share.setOnClickListener {
                viewModel.shareById(post.id)
            }

            arguments?.textArg?.let { text ->
                binding.post.content.text = text
            } ?: run {
                binding.post.content.text = post.content
            }

            //binding.post.content.text = post.content
            binding.post.like.text = post.likedByMe.toString()
            binding.post.author.text = post.author.toString()
            binding.post.published.text = post.published.toString()
            binding.post.share.text = post.shareById.toString()


            //binding.post.video.setOnClickListener {}

            binding.post.menu.setOnClickListener {
                PopupMenu(requireContext(), it).apply {
                    menuInflater.inflate(R.menu.options_post,this.menu)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                viewModel.removeById(post.id)
                                true
                            }

                            R.id.edit -> {
                                viewModel.edit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
        return binding.root
    }
}