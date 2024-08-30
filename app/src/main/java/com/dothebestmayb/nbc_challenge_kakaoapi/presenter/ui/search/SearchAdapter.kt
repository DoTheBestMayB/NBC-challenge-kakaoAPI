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
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchItem
import java.time.format.DateTimeFormatter

class SearchAdapter(
    private val bookmarkOnClickListener: BookmarkOnClickListener,
) : ListAdapter<SearchItem, ViewHolder>(diff) {

    private enum class ItemType(val viewTypeNum: Int) {
        IMAGE(1), VIDEO(2)
    }

    class ImageSearchViewHolder(
        private val binding: ItemImageSearchResultBinding,
        private val bookmarkOnClickListener: BookmarkOnClickListener,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem.Image) {
            binding.ivBookmark.setOnClickListener {
                bookmarkOnClickListener.onClick(item)
            }

            binding.cpiLoading.show()
            binding.ivThumbnail.load(item.thumbnail) {
                target {
                    binding.cpiLoading.hide()
                    binding.ivThumbnail.setImageDrawable(it)
                }
            }
            binding.tvDate.text = item.datetime.format(dateFormat)

            setBookmark(item)
        }

        private fun setBookmark(item: SearchItem.Image) {
            val drawable = when (item.bookmarked) {
                true -> {
                    R.drawable.baseline_bookmark_24
                }

                false -> {
                    R.drawable.baseline_bookmark_border_24
                }
            }
            binding.ivBookmark.load(drawable)
        }
    }

    class VideoSearchViewHolder(
        private val binding: ItemVideoSearchResultBinding,
        private val bookmarkOnClickListener: BookmarkOnClickListener,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem.Video) {
            binding.ivBookmark.setOnClickListener {
                bookmarkOnClickListener.onClick(item)
            }

            binding.ivThumbnail.load(item.thumbnail)
            binding.tvDate.text = item.datetime.format(dateFormat)

            setBookmark(item)
        }

        private fun setBookmark(item: SearchItem.Video) {
            val drawable = when (item.isBookmarked) {
                true -> {
                    R.drawable.baseline_bookmark_24
                }

                false -> {
                    R.drawable.baseline_bookmark_border_24
                }
            }
            binding.ivBookmark.load(drawable)
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
            ItemType.IMAGE.viewTypeNum -> ImageSearchViewHolder(
                binding = ItemImageSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                bookmarkOnClickListener = bookmarkOnClickListener,
            )

            ItemType.VIDEO.viewTypeNum -> VideoSearchViewHolder(
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
            is ImageSearchViewHolder -> holder.bind(getItem(position) as SearchItem.Image)
            is VideoSearchViewHolder -> holder.bind(getItem(position) as SearchItem.Video)
            is NotImplementedYetViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchItem.Image -> ItemType.IMAGE.viewTypeNum
            is SearchItem.Video -> ItemType.VIDEO.viewTypeNum
        }
    }

    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        private val diff = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return if (oldItem is SearchItem.Image && newItem is SearchItem.Image) {
                    oldItem.thumbnail == newItem.thumbnail
                } else if (oldItem is SearchItem.Video && newItem is SearchItem.Video) {
                    oldItem.url == newItem.url
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}