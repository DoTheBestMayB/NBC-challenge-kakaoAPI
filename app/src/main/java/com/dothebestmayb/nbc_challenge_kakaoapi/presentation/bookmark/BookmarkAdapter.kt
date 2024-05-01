package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemBookmarkedBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemUnknownBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.AdapterType
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

class BookmarkAdapter(
    private val onRemove: (mediaInfo: MediaInfo) -> Unit,
) : ListAdapter<MediaInfo, RecyclerView.ViewHolder>(diff) {

    class ImageViewHolder(
        private val binding: ItemBookmarkedBinding,
        private val onRemove: (mediaInfo: MediaInfo) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaInfo.ImageDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvSiteName.text = item.displaySiteName

            ivRemove.setOnClickListener {
                onRemove(item)
            }
        }
    }

    class VideoViewHolder(
        private val binding: ItemBookmarkedBinding,
        private val onRemove: (mediaInfo: MediaInfo) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaInfo.VideoDocumentStatus) = with(binding) {
            Glide.with(root.context)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvSiteName.text = item.author

            ivRemove.setOnClickListener {
                onRemove(item)
            }
        }
    }

    class UnknownViewHolder(
        binding: ItemUnknownBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (AdapterType.from(viewType)) {
            AdapterType.IMAGE -> ImageViewHolder(
                ItemBookmarkedBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onRemove,
            )

            AdapterType.VIDEO -> VideoViewHolder(
                ItemBookmarkedBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onRemove,
            )

            else -> UnknownViewHolder(
                ItemUnknownBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(getItem(position) as MediaInfo.ImageDocumentStatus)
            is VideoViewHolder -> holder.bind(getItem(position) as MediaInfo.VideoDocumentStatus)
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
        }
    }
}