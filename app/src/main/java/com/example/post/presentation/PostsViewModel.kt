package com.example.post.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.post.data.model.Post
import com.example.post.data.model.authorinfo.AuthorInfo
import com.example.post.data.model.comments.CommentInfo
import com.example.post.domain.usecases.GetAuthorInfoUseCase
import com.example.post.domain.usecases.GetCommentsUseCase
import com.example.post.domain.usecases.GetPostsUseCase
import com.example.post.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

import javax.inject.Inject


enum class  PostsApiStatus {LOADING, ERROR, DONE}
enum class  AuthorApiStatus {LOADING, ERROR, DONE}
enum class  CommentsApiStatus {LOADING, ERROR, DONE}


@HiltViewModel
class PostsListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getAuthorInfoUseCase: GetAuthorInfoUseCase,
    private val getCommentsUseCase: GetCommentsUseCase
): ViewModel()  {

    val postsFromNetwork = MutableLiveData<List<Post>>()
    val authorInfoFromAPost = MutableLiveData<AuthorInfo>()
    val commentsFromAPost = MutableLiveData<List<CommentInfo>>()
    val errorMessage = MutableStateFlow<String?>(null)
    val postNetworkStatus = MutableLiveData<PostsApiStatus>()
    val authorNetworkStatus = MutableLiveData<AuthorApiStatus>()
    val commentsNetworkStatus = MutableLiveData<CommentsApiStatus>()


    fun getPosts() {

            getPostsUseCase.invoke().onEach { result ->

                when (result) {
                    is Resource.Success -> {

                        postNetworkStatus.value = PostsApiStatus.DONE

                        withContext(Dispatchers.IO) {
                            postsFromNetwork.postValue(result.data!!)
                        }

                    }
                    is Resource.Error -> {
                        postNetworkStatus.value = PostsApiStatus.ERROR
                        errorMessage.value = result.message
                    }

                    is Resource.Loading -> {
                        postNetworkStatus.value = PostsApiStatus.LOADING
                    }

                }
            }.launchIn(viewModelScope)

    }




    fun getAuthorInfo(userId:String) {

            getAuthorInfoUseCase.invoke(userId).onEach { result ->

                when (result) {
                    is Resource.Success -> {

                        authorNetworkStatus.value = AuthorApiStatus.DONE

                        withContext(Dispatchers.IO) {
                            authorInfoFromAPost.postValue(result.data!!)
                        }

                    }
                    is Resource.Error -> {
                        authorNetworkStatus.value = AuthorApiStatus.ERROR
                    }

                    is Resource.Loading -> {
                        authorNetworkStatus.value = AuthorApiStatus.LOADING
                    }

                }
            }.launchIn(viewModelScope)
    }


    fun getComments(postId:String) {

            getCommentsUseCase.invoke(postId).onEach { result ->

                when (result) {
                    is Resource.Success -> {

                       commentsNetworkStatus.value = CommentsApiStatus.DONE

                        withContext(Dispatchers.IO) {
                            commentsFromAPost.postValue(result.data!!)
                        }


                    }
                    is Resource.Error -> {
                        commentsNetworkStatus.value = CommentsApiStatus.ERROR
                        errorMessage.value = result.message
                    }

                    is Resource.Loading -> {
                        commentsNetworkStatus.value = CommentsApiStatus.LOADING
                    }

                }
            }.launchIn(viewModelScope)
        }





}