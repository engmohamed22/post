package com.example.post.data.remote

import com.example.post.data.model.Post
import com.example.post.data.model.authorinfo.AuthorInfo
import com.example.post.data.model.comments.CommentInfo
import com.example.post.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceCalls {

    @GET(Constants.POST_URL)
    suspend fun getPosts() : List<Post>

    @GET("users/{id}")
    suspend fun getAuthorInfo(@Path("id")Id: String) : AuthorInfo


    @GET("comments")
    suspend fun getComments(@Query("postId")postId: String) : List<CommentInfo>
}