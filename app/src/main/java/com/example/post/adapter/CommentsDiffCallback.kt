package com.example.post.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.post.data.model.comments.CommentInfo


class CommentsDiffCallback : DiffUtil.ItemCallback<CommentInfo>(){
    override fun areItemsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem == newItem
    }
}