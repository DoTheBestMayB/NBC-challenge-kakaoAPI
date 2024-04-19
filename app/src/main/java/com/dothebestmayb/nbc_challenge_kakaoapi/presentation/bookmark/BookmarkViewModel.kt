package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val checkMediaIsBookmarkedUseCase: CheckMediaIsBookmarkedUseCase,
    private val deleteBookmarkedImageUseCase: DeleteBookmarkedImageUseCase,
    private val getAllBookmarkedImageUseCase: GetAllBookmarkedImageUseCase,
    private val insertBookmarkedImageUseCase: InsertBookmarkedImageUseCase,
) : ViewModel() {

    private val _bookmarkedImageDocuments = MutableLiveData<List<ImageDocumentStatus>>()
    val bookmarkedImageDocuments: LiveData<List<ImageDocumentStatus>>
        get() = _bookmarkedImageDocuments

    fun fetch() {
        viewModelScope.launch {
            _bookmarkedImageDocuments.value =
                getAllBookmarkedImageUseCase().map { it.toWithBookmarked(true) }
        }
    }

    fun remove(mediaInfo: MediaInfo) {
        when (mediaInfo) {
            is ImageDocumentStatus -> remove(mediaInfo)
            is VideoDocumentStatus -> remove(mediaInfo)
        }
    }

    private fun remove(item: ImageDocumentStatus) {
        viewModelScope.launch {
            deleteBookmarkedImageUseCase(item.toEntity())
        }
        val values = _bookmarkedImageDocuments.value?.toMutableList() ?: return
        values.remove(item)
        _bookmarkedImageDocuments.value = values
    }

    private fun remove(item: VideoDocumentStatus) {
//        viewModelScope.launch {
//            deleteBookmarkedVideoUseCase(item.toEntity())
//        }
//        val values = _bookmarkedVideoDocuments.value?.toMutableList() ?: return
//        values.remove(item)
//        _bookmarkedVideoDocuments.value = values.drop(index)
    }
}