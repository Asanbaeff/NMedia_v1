package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.core.net.toUri


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel: PostViewModel by viewModels()

//        val newPostLauncher = registerForActivityResult(NewPostResultContract) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.save(result)
//        }


        val newPostLauncher = registerForActivityResult(NewPostResultContract) { result ->
            if (result == null) {
                viewModel.cancelEdit()
            } else {
                viewModel.save(result)
            }
        }


        val adapter = PostsAdapter(object : OnInteractionListener {

            override fun onEdit(post: Post) {
                val content = post.content
                newPostLauncher.launch(content)
                viewModel.edit(post)

            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                post.content.let { content ->
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, content)
                        type = "text/plain"
                    }
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                }


            }

            override fun onPlayVideo(post: Post) { // Работа с видео
                val intent = Intent(Intent.ACTION_VIEW, post.videoUrl.toUri())
                //if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                //} else {
                // Toast.makeText(
                // this@MainActivity,
                // Toast.LENGTH_SHORT
                // ).show()
                // }
            }

        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch("")
        }

    }
}
