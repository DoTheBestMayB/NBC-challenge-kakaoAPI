package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

data class VideoSearchEntity(
    val metaResponse: MetaEntity,
    val documents: List<VideoDocumentEntity>,
)
