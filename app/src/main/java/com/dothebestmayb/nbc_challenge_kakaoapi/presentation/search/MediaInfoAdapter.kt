package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.adapter.MediaInfoOnClickListener
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfoBookmarkActionType
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus
import com.google.android.material.materialswitch.MaterialSwitch

class MediaInfoAdapter(
    private val mediaInfoOnClickListener: MediaInfoOnClickListener,
    private val type: MediaInfoBookmarkActionType,
) : ListAdapter<MediaInfo, RecyclerView.ViewHolder>(diff) {

    enum class PayLoad {
        ONLY_BOOKMARK
    }

    inner class ImageViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvSiteName.text = item.displaySiteName
            msBookmark.isChecked = item.isBookmarked
            msBookmark.setOnCheckedChangeListener { _, isChecked ->
                when (type) {
                    MediaInfoBookmarkActionType.REMOVE -> mediaInfoOnClickListener.remove(item)
                    MediaInfoBookmarkActionType.STAY -> mediaInfoOnClickListener.onBookmarkChanged(
                        item,
                        isChecked
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE_TYPE -> ImageViewHolder(
                ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
//            VIDEO_TYPE -> VideoViewHolder(
//                ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            )
            else -> throw IllegalArgumentException("Not implemented yet")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is ImageViewHolder -> holder.bind(getItem(position) as ImageDocumentStatus)
            else -> {
                TODO()
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        for (payload in payloads) {
            when (payload) {
                PayLoad.ONLY_BOOKMARK -> continue // 북마크 여부만 바뀐경우 업데이트하지 않음
                else -> {
                    super.onBindViewHolder(holder, position, payloads)
                    return
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ImageDocumentStatus -> IMAGE_TYPE
            is VideoDocumentStatus -> VIDEO_TYPE
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        holder.itemView.findViewById<MaterialSwitch>(R.id.ms_bookmark)
            .setOnCheckedChangeListener(null)
    }

    companion object {

        private const val IMAGE_TYPE = 0
        private const val VIDEO_TYPE = 1

        val diff = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean {
                return if (oldItem is ImageDocumentStatus && newItem is ImageDocumentStatus) {
                    oldItem.imageUrl == newItem.imageUrl
                } else if (oldItem is VideoDocumentStatus && newItem is VideoDocumentStatus) {
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
                if (oldItem is ImageDocumentStatus && newItem is ImageDocumentStatus && checkDiffOnlyBookmark(
                        oldItem,
                        newItem
                    )
                ) {
                    return PayLoad.ONLY_BOOKMARK
                } else if (oldItem is VideoDocumentStatus && newItem is VideoDocumentStatus && checkDiffOnlyBookmark(
                        oldItem,
                        newItem
                    )
                ) {
                    return PayLoad.ONLY_BOOKMARK
                }
                return null
            }

            private fun checkDiffOnlyBookmark(
                oldItem: ImageDocumentStatus,
                newItem: ImageDocumentStatus
            ): Boolean {
                if (oldItem.collection != newItem.collection) return false
                if (oldItem.thumbnailUrl != newItem.thumbnailUrl) return false
                if (oldItem.imageUrl != newItem.imageUrl) return false
                if (oldItem.width != newItem.width) return false
                if (oldItem.height != newItem.height) return false
                if (oldItem.displaySiteName != newItem.displaySiteName) return false
                if (oldItem.docUrl != newItem.docUrl) return false
                if (oldItem.datetime != newItem.datetime) return false
                return oldItem.isBookmarked != newItem.isBookmarked
            }

            private fun checkDiffOnlyBookmark(
                oldItem: VideoDocumentStatus,
                newItem: VideoDocumentStatus
            ): Boolean {
                if (oldItem.title != newItem.title) return false
                if (oldItem.url != newItem.url) return false
                if (oldItem.datetime != newItem.datetime) return false
                if (oldItem.playTime != newItem.playTime) return false
                if (oldItem.thumbnail != newItem.thumbnail) return false
                if (oldItem.author != newItem.author) return false
                return oldItem.isBookmarked != newItem.isBookmarked
            }
        }
    }
}