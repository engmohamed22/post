package com.example.post.di


import com.example.post.data.repository.PostRepositoryImpl
import com.example.post.domain.repository.PostsRepository
import com.example.post.data.remote.ApiServiceCalls
import com.example.post.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun httpClient(): OkHttpClient{

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClientWithHeader = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return okHttpClientWithHeader
    }


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(httpClient())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiServiceCalls =
        retrofit.create(ApiServiceCalls::class.java)


    @Provides
    @Singleton
    fun providesPostsRepository(api: ApiServiceCalls): PostsRepository {
        return PostRepositoryImpl(api)
    }




}