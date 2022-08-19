package com.example.post.data.model.authorinfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    @SerializedName("bs")
    val bs: String?,
    @SerializedName("catchPhrase")
    val catchPhrase: String?,
    @SerializedName("name")
    val name: String?
): Parcelable