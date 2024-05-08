package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.DateUtil
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemImageSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemUnknownBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemVideoSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.AdapterType
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

class SearchAdapter(
    private val onBookmarkChanged: (mediaInfo: MediaInfo, isBookmarked: Boolean) -> Unit,
) : ListAdapter<MediaInfo, ViewHolder>(diff) {

    enum class PayLoad {
        ONLY_BOOKMARK
    }

    class ImageViewHolder(
        private val binding: ItemImageSearchResultBinding,
        private val onBookmark: (mediaInfo: MediaInfo, isBookmarked: Boolean) -> Unit,
    ) : ViewHolder(binding.root) {

        fun bind(item: MediaInfo.ImageDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvUploadTime.text = DateUtil.simpleFormatDate(item.dateTime)

            val displaySiteName = "$IMAGE_HEADER_TEXT ${item.displaySiteName}"
            tvSiteName.text = displaySiteName
            changeBookmarkInfo(item)
        }

        fun changeBookmarkInfo(item: MediaInfo.ImageDocumentStatus) = with(binding) {
            if (item.isBookmarked) {
                ivBookmark.setImageResource(R.drawable.baseline_bookmark_added_24)
            } else {
                ivBookmark.setImageResource(R.drawable.bookmark_add_24)

            }
            ivBookmark.setOnClickListener {
                onBookmark(item, item.isBookmarked.not())
            }
        }

        companion object {
            private const val IMAGE_HEADER_TEXT = "[Image]"
        }
    }

    class VideoViewHolder(
        private val binding: ItemVideoSearchResultBinding,
        private val onBookmark: (mediaInfo: MediaInfo, isBookmarked: Boolean) -> Unit,
    ) : ViewHolder(binding.root) {

        fun bind(item: MediaInfo.VideoDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbNail)

            setPlayTime(item.playTime, tvPlayTime)
            val displayTitleName = "$VIDEO_HEADER_TEXT ${item.title}"
            tvTitle.text = displayTitleName
            tvSiteName.text = item.author
            tvUploadTime.text = DateUtil.simpleFormatDate(item.dateTime)
            changeBookmarkInfo(item)
        }

        fun changeBookmarkInfo(item: MediaInfo.VideoDocumentStatus) = with(binding) {
            if (item.isBookmarked) {
                ivBookmark.setImageResource(R.drawable.baseline_bookmark_added_24)
            } else {
                ivBookmark.setImageResource(R.drawable.bookmark_add_24)

            }
            ivBookmark.setOnClickListener {
                onBookmark(item, item.isBookmarked.not())
            }
        }

        private fun setPlayTime(playTime: Int, view: TextView) {
            val hours = playTime / 3600
            val minutes = (playTime % 3600) / 60
            val seconds = playTime % 60

            val formattedTime = if (hours > 0) {
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format("%02d:%02d", minutes, seconds)
            }

            view.text = formattedTime
        }

        companion object {
            private const val VIDEO_HEADER_TEXT = "[Video]"
        }
    }

    class UnknownViewHolder(
        binding: ItemUnknownBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (AdapterType.from(viewType)) {
            AdapterType.IMAGE -> ImageViewHolder(
                ItemImageSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onBookmarkChanged,
            )

            AdapterType.VIDEO -> VideoViewHolder(
                ItemVideoSearchResultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onBookmarkChanged,
            )

            else -> UnknownViewHolder(
                ItemUnknownBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(getItem(position) as MediaInfo.ImageDocumentStatus)
            is VideoViewHolder -> holder.bind(getItem(position) as MediaInfo.VideoDocumentStatus)
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        for (payload in payloads) {
            when (payload) {
                PayLoad.ONLY_BOOKMARK -> {
                    when (holder) {
                        is ImageViewHolder -> holder.changeBookmarkInfo(getItem(position) as MediaInfo.ImageDocumentStatus)
                        is VideoViewHolder -> holder.changeBookmarkInfo(getItem(position) as MediaInfo.VideoDocumentStatus)
                    }
                }

                else -> {
                    super.onBindViewHolder(holder, position, payloads)
                    return
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MediaInfo.ImageDocumentStatus -> AdapterType.IMAGE.viewTypeValue
            is MediaInfo.VideoDocumentStatus -> AdapterType.VIDEO.viewTypeValue
        }
    }

    companion object {

        val diff = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean {
                return if (oldItem is MediaInfo.ImageDocumentStatus && newItem is MediaInfo.ImageDocumentStatus) {
                    oldItem.docUrl == newItem.docUrl
                } else if (oldItem is MediaInfo.VideoDocumentStatus && newItem is MediaInfo.VideoDocumentStatus) {
                    oldItem.url == newItem.url
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean {
                return oldItem == newItem
            }

            // 북마킹 여부만 바뀌었는지를 판별하기 위함
            override fun getChangePayload(oldItem: MediaInfo, newItem: MediaInfo): Any? {
                if (oldItem is MediaInfo.ImageDocumentStatus && newItem is MediaInfo.ImageDocumentStatus && checkDiffOnlyBookmark(
                        oldItem,
                        newItem
                    )
                ) {
                    return PayLoad.ONLY_BOOKMARK
                } else if (oldItem is MediaInfo.VideoDocumentStatus && newItem is MediaInfo.VideoDocumentStatus && checkDiffOnlyBookmark(
                        oldItem,
                        newItem
                    )
                ) {
                    return PayLoad.ONLY_BOOKMARK
                }
                return null
            }

            private fun checkDiffOnlyBookmark(
                oldItem: MediaInfo.ImageDocumentStatus,
                newItem: MediaInfo.ImageDocumentStatus
            ): Boolean {
                if (oldItem.collection != newItem.collection) return false
                if (oldItem.thumbnailUrl != newItem.thumbnailUrl) return false
                if (oldItem.imageUrl != newItem.imageUrl) return false
                if (oldItem.width != newItem.width) return false
                if (oldItem.height != newItem.height) return false
                if (oldItem.displaySiteName != newItem.displaySiteName) return false
                if (oldItem.docUrl != newItem.docUrl) return false
                if (oldItem.dateTime != newItem.dateTime) return false
                return oldItem.isBookmarked != newItem.isBookmarked
            }

            private fun checkDiffOnlyBookmark(
                oldItem: MediaInfo.VideoDocumentStatus,
                newItem: MediaInfo.VideoDocumentStatus
            ): Boolean {
                if (oldItem.title != newItem.title) return false
                if (oldItem.url != newItem.url) return false
                if (oldItem.dateTime != newItem.dateTime) return false
                if (oldItem.playTime != newItem.playTime) return false
                if (oldItem.thumbnailUrl != newItem.thumbnailUrl) return false
                if (oldItem.author != newItem.author) return false
                return oldItem.isBookmarked != newItem.isBookmarked
            }
        }
    }
}