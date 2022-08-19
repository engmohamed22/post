package com.example.post.domain.repository

import com.example.post.data.model.Post
import com.example.post.data.model.authorinfo.AuthorInfo
import com.example.post.data.model.comments.CommentInfo

interface PostsRepository {

    suspend fun getPosts(): List<Post>

    suspend fun getAuthorInfo(userId:String): AuthorInfo

    suspend fun getComments(postId: String): List<CommentInfo>
}