package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ItemSearchResultBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentsEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentsEntity

class SearchAdapter : ListAdapter<MediaInfo, RecyclerView.ViewHolder>(diff) {

    class ImageViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDocumentsEntity) = with(binding) {
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.transparent_background)
                .into(ivThumbnail)
            tvSiteName.text = item.displaySiteName
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
            is ImageViewHolder -> holder.bind(getItem(position) as ImageDocumentsEntity)
            else -> {
                TODO()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ImageDocumentsEntity -> IMAGE_TYPE
            is VideoDocumentsEntity -> VIDEO_TYPE
        }
    }

    companion object {

        private const val IMAGE_TYPE = 0
        private const val VIDEO_TYPE = 1

        val diff = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean {
                return if (oldItem is ImageDocumentsEntity && newItem is ImageDocumentsEntity) {
                    oldItem.imageUrl == newItem.imageUrl
                } else if (oldItem is VideoDocumentsEntity && newItem is VideoDocumentsEntity) {
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