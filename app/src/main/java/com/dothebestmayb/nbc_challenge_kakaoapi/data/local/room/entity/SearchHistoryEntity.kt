package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchHistoryInfo
import java.time.LocalDateTime

@Entity
data class SearchHistoryEntity(
    @PrimaryKey
    val query: String,
    val datetime: LocalDateTime,
) {

    fun toDomain(): SearchHistoryInfo = SearchHistoryInfo(
        query = query,
        datetime = datetime,
    )
}
