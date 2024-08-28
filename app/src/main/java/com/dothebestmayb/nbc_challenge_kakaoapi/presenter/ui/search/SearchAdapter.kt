package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemImageSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemNotYetImplementSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemVideoSearchResultBinding

class SearchAdapter(
    private val bookmarkOnClickListener: BookmarkOnClickListener,
) : ListAdapter<SearchItem, ViewHolder>(diff) {

    class ImageSearchViewHolder(
        private val binding: ItemImageSearchResultBinding,
        private val bookmarkOnClickListener: BookmarkOnClickListener,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem) {
            binding.btnBookmark.setOnClickListener {
                bookmarkOnClickListener.onClick(item)
            }

            binding.ivThumbnail.load(item.thumbnail)
            binding.tvDate.text = item.datetime.toString()

            setBookmark(item)
        }

        private fun setBookmark(item: SearchItem) {
            val drawable = when(item.isBookmarked) {
                true -> {
                    R.drawable.baseline_bookmark_24
                }
                false -> {
                    R.drawable.baseline_bookmark_border_24
                }
            }
            binding.ivThumbnail.load(drawable)
        }
    }

    class VideoSearchViewHolder(
        private val binding: ItemVideoSearchResultBinding,
        private val bookmarkOnClickListener: BookmarkOnClickListener,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem) {
            binding.btnBookmark.setOnClickListener {
                bookmarkOnClickListener.onClick(item)
            }

            binding.ivThumbnail.load(item.thumbnail)
            binding.tvDate.text = item.datetime.toString()

            setBookmark(item)
        }

        private fun setBookmark(item: SearchItem) {
            val drawable = when(item.isBookmarked) {
                true -> {
                    R.drawable.baseline_bookmark_24
                }
                false -> {
                    R.drawable.baseline_bookmark_border_24
                }
            }
            binding.ivThumbnail.load(drawable)
        }
    }

    class NotImplementedYetViewHolder(
        binding: ItemNotYetImplementSearchResultBinding,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SearchType.IMAGE.viewTypeNum -> ImageSearchViewHolder(
                binding = ItemImageSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                bookmarkOnClickListener = bookmarkOnClickListener,
            )

            SearchType.VIDEO.viewTypeNum -> VideoSearchViewHolder(
                binding = ItemVideoSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                bookmarkOnClickListener = bookmarkOnClickListener,
            )

            else -> NotImplementedYetViewHolder(
                binding = ItemNotYetImplementSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ImageSearchViewHolder -> holder.bind(getItem(position))
            is VideoSearchViewHolder -> holder.bind(getItem(position))
            is NotImplementedYetViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            SearchType.IMAGE -> SearchType.IMAGE.viewTypeNum
            SearchType.VIDEO -> SearchType.VIDEO.viewTypeNum
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem.thumbnail == newItem.thumbnail
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}