package com.example.post.domain.usecases

import com.example.post.data.model.comments.CommentInfo
import com.example.post.domain.repository.PostsRepository
import com.example.post.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor (private val repository: PostsRepository) {


    operator fun invoke (postId: String): Flow<Resource<List<CommentInfo>>> = flow {

        try {
            emit(Resource.Loading<List<CommentInfo>>())

            val comments = repository.getComments(postId)

            emit(Resource.Success(comments))

        }catch (e:HttpException){

            emit(Resource.Error<List<CommentInfo>>(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){

            emit(Resource.Error<List<CommentInfo>>("Couldn't reach the server, Check your internet connection and try again"))
        }
    }
}