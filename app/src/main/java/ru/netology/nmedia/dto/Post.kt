package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    var likes: Int = 0,
    val shareById: Int,
    var videoUrl: String,
    var views: Int = 0,
)





