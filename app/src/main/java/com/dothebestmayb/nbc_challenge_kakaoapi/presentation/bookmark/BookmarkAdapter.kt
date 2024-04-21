package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemBookmarkedBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemHeaderBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.MediaInfoOnClickListener
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.HeaderStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.HeaderType
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus

class BookmarkAdapter(
    private val mediaInfoOnClickListener: MediaInfoOnClickListener,
) : ListAdapter<MediaInfo, RecyclerView.ViewHolder>(diff) {

    inner class ImageViewHolder(private val binding: ItemBookmarkedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvSiteName.text = item.displaySiteName

            ivRemove.setOnClickListener {
                mediaInfoOnClickListener.remove(item)
            }
        }
    }

    inner class VideoViewHolder(private val binding: ItemBookmarkedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VideoDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.thumbnail)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvSiteName.text = item.author

            ivRemove.setOnClickListener {
                mediaInfoOnClickListener.remove(item)
            }
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HeaderStatus) = with(binding) {
            tvName.text = when (item.type) {
                HeaderType.IMAGE -> binding.root.context.getString(R.string.image)
                HeaderType.VIDEO -> binding.root.context.getString(R.string.video)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE_TYPE -> ImageViewHolder(
                ItemBookmarkedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            VIDEO_TYPE -> VideoViewHolder(
                ItemBookmarkedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            HEADER_TYPE -> HeaderViewHolder(
                ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Not implemented yet")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(getItem(position) as ImageDocumentStatus)
            is VideoViewHolder -> holder.bind(getItem(position) as VideoDocumentStatus)
            is HeaderViewHolder -> holder.bind(getItem(position) as HeaderStatus)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ImageDocumentStatus -> IMAGE_TYPE
            is VideoDocumentStatus -> VIDEO_TYPE
            is HeaderStatus -> HEADER_TYPE
        }
    }

    companion object {

        private const val IMAGE_TYPE = 0
        private const val VIDEO_TYPE = 1
        private const val HEADER_TYPE = 2

        val diff = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean {
                return if (oldItem is ImageDocumentStatus && newItem is ImageDocumentStatus) {
                    oldItem.docUrl == newItem.docUrl
                } else if (oldItem is VideoDocumentStatus && newItem is VideoDocumentStatus) {
                    oldItem.url == newItem.url
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}