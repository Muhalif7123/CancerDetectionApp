package com.dicoding.asclepius.view.history

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.databinding.CardHistoryBinding

class HistoryAdapter : ListAdapter<HistoryEntity, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onClickDelete: ((HistoryEntity) -> Unit)? = null

    class MyViewHolder(val binding: CardHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryEntity) {
            binding.ivPhoto.setImageURI(Uri.parse(history.photo))
            binding.tvResult.text = history.result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val holderItem = getItem(position)
        holder.bind(holderItem)
        holder.binding.deleteButton.setOnClickListener {
            onClickDelete?.let { it1 -> it1(holderItem) }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HistoryEntity> =
            object : DiffUtil.ItemCallback<HistoryEntity>() {
                override fun areItemsTheSame(
                    oldItem: HistoryEntity,
                    newItem: HistoryEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: HistoryEntity,
                    newItem: HistoryEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}