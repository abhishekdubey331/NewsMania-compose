package com.clean.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.clean.newsapp.data.model.NewsItem
import com.clean.newsapp.databinding.LayoutNewsItemBinding
import com.clean.newsapp.extensions.loadImage
import com.clean.newsapp.util.DateTimeUtil
import javax.inject.Inject

class NewsListAdapter @Inject constructor(
    private val dateTimeUtil: DateTimeUtil,
    private val newsItemListener: (Int) -> Unit
) : ListAdapter<NewsItem, NewsListAdapter.NewsViewHolder>(object :
    DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(
        oldItem: NewsItem, newItem: NewsItem
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: NewsItem, newItem: NewsItem
    ) = oldItem.id == newItem.id
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return LayoutNewsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .run { NewsViewHolder(this) }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        val binding = holder.binding
        with(item) {
            binding.newsMediaImv.loadImage(newsMediaImageUrl)
            binding.newsTitleTextview.text = title
            binding.newsAuthorTextview.text = author
            binding.publishedTextview.text = dateTimeUtil.uiTimeFormat(publishedAt)
        }
        holder.itemView.setOnClickListener {
            newsItemListener.invoke(item.id)
        }
    }

    class NewsViewHolder(val binding: LayoutNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
