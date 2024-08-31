package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemSearchHistoryBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchHistory
import java.time.format.DateTimeFormatter

class SearchHistoryAdapter(
    private val searchHistoryOnClickListener: SearchHistoryOnClickListener,
) : ListAdapter<SearchHistory, SearchHistoryAdapter.SearchViewHolder>(diff) {

    class SearchViewHolder(
        private val binding: ItemSearchHistoryBinding,
        private val onClick: (String) -> Unit,
        private val onDelete: (String) -> Unit,
    ) : ViewHolder(binding.root) {



        fun bind(item: SearchHistory) {
            binding.tvText.setOnClickListener {
                onClick(item.query)
            }
            binding.ivDelete.setOnClickListener {
                onDelete(item.query)
            }

            binding.tvText.text = item.query
            binding.tvDate.text = item.datetime.format(dateFormat)
        }

        companion object {
            private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            binding = ItemSearchHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = searchHistoryOnClickListener::onClick,
            onDelete = searchHistoryOnClickListener::onDelete
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<SearchHistory>() {
            override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean {
                return oldItem.query == newItem.query
            }

            override fun areContentsTheSame(
                oldItem: SearchHistory,
                newItem: SearchHistory
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}