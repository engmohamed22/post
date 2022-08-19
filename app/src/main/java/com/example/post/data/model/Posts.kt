package com.example.post.data.model
import android.os.Parcelable
import com.example.post.data.model.authorinfo.AuthorInfo
import com.example.post.data.model.comments.CommentInfo
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Post(
    @SerializedName("body")
    val body: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?,
    var authorInfo: AuthorInfo? = null,
    var commentInfo: List<CommentInfo>? = null,
): Parcelable