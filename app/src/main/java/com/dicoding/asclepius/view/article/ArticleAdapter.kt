package com.dicoding.asclepius.view.article

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.CardRecyclerViewBinding

class ArticleAdapter : ListAdapter<ArticlesItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: CardRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(article: ArticlesItem) {
            binding.tvTitle.text = article.title
            binding.tvDesc.text = article.description.toString()
            binding.tvAuthor.text = article.author
            binding.tvDate.text = article.publishedAt
            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .into(binding.ivPhoto)
            binding.cardArticle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CardRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val viewHolderItem = getItem(position)
        holder.bindItem(viewHolderItem)

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticlesItem> =
            object : DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}