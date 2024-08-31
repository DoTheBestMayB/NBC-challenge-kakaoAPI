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
    private val searchOnClickListener: SearchOnClickListener,
) : ListAdapter<SearchItem, ViewHolder>(diff) {

    private enum class ItemType(val viewTypeNum: Int) {
        IMAGE(1), VIDEO(2)
    }

    class ImageSearchViewHolder(
        private val binding: ItemImageSearchResultBinding,
        private val onBookmarkClick: (SearchItem) -> Unit,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem.Image) {
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

        fun setBookmark(item: SearchItem.Image) {
            binding.ivBookmark.setOnClickListener {
                onBookmarkClick(item)
            }
            val drawable = when (item.bookmarked) {
                true -> {
                    R.drawable.baseline_bookmark_24
                }

                false -> {
                    R.drawable.baseline_bookmark_border_24
                }
            }
            binding.ivBookmark.setImageResource(drawable)
        }
    }

    class VideoSearchViewHolder(
        private val binding: ItemVideoSearchResultBinding,
        private val onBookmarkClick: (SearchItem) -> Unit,
    ) : ViewHolder(binding.root) {

        fun bind(item: SearchItem.Video) {
            binding.ivThumbnail.load(item.thumbnail)
            binding.tvDate.text = item.datetime.format(dateFormat)

            setBookmark(item)
        }

        fun setBookmark(item: SearchItem.Video) {
            binding.ivBookmark.setOnClickListener {
                onBookmarkClick(item)
            }

            val drawable = when (item.bookmarked) {
                true -> {
                    R.drawable.baseline_bookmark_24
                }

                false -> {
                    R.drawable.baseline_bookmark_border_24
                }
            }
            binding.ivBookmark.setImageResource(drawable)
        }
    }

    class NotImplementedYetViewHolder(
        binding: ItemNotYetImplementSearchResultBinding,
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.IMAGE.viewTypeNum -> ImageSearchViewHolder(
                binding = ItemImageSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onBookmarkClick = searchOnClickListener::onBookmarkClick,
            )

            ItemType.VIDEO.viewTypeNum -> VideoSearchViewHolder(
                binding = ItemVideoSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onBookmarkClick = searchOnClickListener::onBookmarkClick,
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
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        for (payload in payloads) {
            if ((payload as Boolean)) {
                when (holder) {
                    is ImageSearchViewHolder -> holder.setBookmark(getItem(position) as SearchItem.Image)
                    is VideoSearchViewHolder -> holder.setBookmark(getItem(position) as SearchItem.Video)
                    else -> {
                        super.onBindViewHolder(holder, position, payloads)
                        return
                    }
                }
            }
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

            // 북마크 여부만 바뀐 경우 대응
            override fun getChangePayload(oldItem: SearchItem, newItem: SearchItem): Boolean {
                return when (oldItem) {
                    is SearchItem.Image -> {
                        oldItem == (newItem as SearchItem.Image).copy(bookmarked = newItem.bookmarked.not())
                    }

                    is SearchItem.Video -> {
                        oldItem == (newItem as SearchItem.Video).copy(bookmarked = newItem.bookmarked.not())
                    }
                }
            }

        }
    }
}