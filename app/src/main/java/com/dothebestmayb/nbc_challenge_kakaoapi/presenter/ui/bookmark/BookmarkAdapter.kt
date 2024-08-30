package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemImageBookmarkBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemNotYetImplementSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemVideoBookmarkBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark.model.BookmarkItem
import java.time.format.DateTimeFormatter

class BookmarkAdapter(
    private val bookmarkOnClickListener: BookmarkOnClickListener,
) : ListAdapter<BookmarkItem, ViewHolder>(diff) {

    private enum class ItemType(val viewTypeNum: Int) {
        IMAGE(1), VIDEO(2)
    }

    class ImageBookmarkViewHolder(
        private val binding: ItemImageBookmarkBinding,
        private val bookmarkOnClickListener: BookmarkOnClickListener,
    ) : ViewHolder(binding.root) {

        fun bind(item: BookmarkItem.Image) {
            binding.ivBookmark.setOnClickListener {
                bookmarkOnClickListener.onBookmarkClick(item)
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

        private fun setBookmark(item: BookmarkItem.Image) {
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

    class VideoBookmarkViewHolder(
        private val binding: ItemVideoBookmarkBinding,
        private val bookmarkOnClickListener: BookmarkOnClickListener,
    ) : ViewHolder(binding.root) {

        fun bind(item: BookmarkItem.Video) {
            binding.ivBookmark.setOnClickListener {
                bookmarkOnClickListener.onBookmarkClick(item)
            }

            binding.ivThumbnail.load(item.thumbnail)
            binding.tvDate.text = item.datetime.format(dateFormat)

            setBookmark(item)
        }

        private fun setBookmark(item: BookmarkItem.Video) {
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

    class NotImplementedYetViewHolder(
        binding: ItemNotYetImplementSearchResultBinding,
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.IMAGE.viewTypeNum -> ImageBookmarkViewHolder(
                binding = ItemImageBookmarkBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                bookmarkOnClickListener = bookmarkOnClickListener,
            )

            ItemType.VIDEO.viewTypeNum -> VideoBookmarkViewHolder(
                binding = ItemVideoBookmarkBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                bookmarkOnClickListener = bookmarkOnClickListener,
            )

            else -> NotImplementedYetViewHolder(
                ItemNotYetImplementSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ImageBookmarkViewHolder -> holder.bind(getItem(position) as BookmarkItem.Image)
            is VideoBookmarkViewHolder -> holder.bind(getItem(position) as BookmarkItem.Video)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BookmarkItem.Image -> ItemType.IMAGE.viewTypeNum
            is BookmarkItem.Video -> ItemType.VIDEO.viewTypeNum
        }
    }

    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        private val diff = object : DiffUtil.ItemCallback<BookmarkItem>() {
            override fun areItemsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean {
                return if (oldItem is BookmarkItem.Image && newItem is BookmarkItem.Image) {
                    oldItem.thumbnail == newItem.thumbnail
                } else if (oldItem is BookmarkItem.Video && newItem is BookmarkItem.Video) {
                    oldItem.url == newItem.url
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}
