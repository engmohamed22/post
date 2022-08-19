package com.example.post.data.repository

import com.example.post.data.model.Post
import com.example.post.data.model.authorinfo.AuthorInfo
import com.example.post.data.model.comments.CommentInfo
import com.example.post.domain.repository.PostsRepository
import com.example.post.data.remote.ApiServiceCalls
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api : ApiServiceCalls) : PostsRepository {

    override suspend fun getPosts(): List<Post>{
       return api.getPosts()
    }

    override suspend fun getAuthorInfo(userId:String): AuthorInfo {
        return api.getAuthorInfo(userId)
    }

    override suspend fun getComments(postId: String): List<CommentInfo> {
        return api.getComments(postId)
    }


}