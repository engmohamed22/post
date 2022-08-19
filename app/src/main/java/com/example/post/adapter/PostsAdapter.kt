package com.example.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.post.data.model.Post
import com.example.post.utils.HashUtils.sha256
import com.example.post.R
import com.example.post.databinding.PostListItemBinding


typealias urlListener = (item: Post) -> Unit


class PostsAdapter  (val listener: urlListener)  :ListAdapter<Post, PostsAdapter.ImageViewHolder>(
    PostsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)

        return ImageViewHolder(PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
            getItem(it)?.let{item-> listener(item)}
        }

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                title.text = "Title: ${currentItem.title?.take(20)}"
                body.text = "Body: ${currentItem.body?.take(100)}"
                if (currentItem.authorInfo?.name.isNullOrEmpty()){
                    authorName.text = "John doe"
                }else{
                    authorName.text = currentItem?.authorInfo?.name
                }

                if (currentItem.commentInfo.isNullOrEmpty()){
                    totalComments.text = "0"
                }else{
                    totalComments.text = currentItem?.commentInfo?.size.toString()
                }


                val imageLink = currentItem?.title
                val hashedLinkForSeed = imageLink?.let { sha256(it) }
                val getImageUrl = "https://picsum.photos/seed/$hashedLinkForSeed/200/200"

                imageThumbail.load(getImageUrl){
                    error(R.drawable.ic_baseline_error_outline_24)
                    placeholder(R.drawable.ic_baseline_chat_24)
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    inner class ImageViewHolder(val binding: PostListItemBinding, private val listener: (Int)-> Unit): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener(adapterPosition)
            }
        }
    }


    interface RowClickListener{
        fun onItemClickListener(car: Post)

    }

}