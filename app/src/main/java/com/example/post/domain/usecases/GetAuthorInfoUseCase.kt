package com.example.post.domain.usecases

import com.example.post.data.model.authorinfo.AuthorInfo
import com.example.post.domain.repository.PostsRepository
import com.example.post.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAuthorInfoUseCase @Inject constructor (private val repository: PostsRepository) {


    operator fun invoke (userId:String): Flow<Resource<AuthorInfo>> = flow {

        try {
            emit(Resource.Loading<AuthorInfo>())

            val comments = repository.getAuthorInfo(userId)

            emit(Resource.Success(comments))

        }catch (e:HttpException){

            emit(Resource.Error<AuthorInfo>(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){

            emit(Resource.Error<AuthorInfo>("Couldn't reach the server, Check your internet connection and try again"))
        }
    }
}