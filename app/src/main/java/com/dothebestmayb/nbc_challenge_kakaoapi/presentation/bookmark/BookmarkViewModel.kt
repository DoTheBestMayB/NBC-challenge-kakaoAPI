package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val checkMediaIsBookmarkedUseCase: CheckMediaIsBookmarkedUseCase,
    private val deleteBookmarkedImageUseCase : DeleteBookmarkedImageUseCase,
    private val getAllBookmarkedImageUseCase : GetAllBookmarkedImageUseCase,
    private val insertBookmarkedImageUseCase : InsertBookmarkedImageUseCase,
): ViewModel() {

    private val _bookmarkedImageDocuments = MutableLiveData<List<ImageDocumentEntity>>()
    val bookmarkedImageDocuments: LiveData<List<ImageDocumentEntity>>
        get() = _bookmarkedImageDocuments

    fun fetch() {
        viewModelScope.launch {
            val imageDocumentEntities = getAllBookmarkedImageUseCase()
            _bookmarkedImageDocuments.value = imageDocumentEntities
        }
    }

}