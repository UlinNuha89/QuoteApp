package com.lynn.quoteapp.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynn.quoteapp.data.model.Quote
import com.lynn.quoteapp.databinding.ItemQuoteBinding

class MainAdapter() : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Quote>() {
                override fun areItemsTheSame(
                    oldItem: Quote,
                    newItem: Quote
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Quote,
                    newItem: Quote
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemQuoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size


    fun submitData(data: List<Quote>) {
        dataDiffer.submitList(data)
    }

    class MainViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Quote) {
            binding.tvQuote.text = "\"${item.quote}\""
            binding.tvAnime.text = "From - ${item.anime}"
            binding.tvCharacter.text = "Said by - ${item.character}"
        }
    }
}