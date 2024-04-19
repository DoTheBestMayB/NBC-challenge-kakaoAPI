package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

data class ImageSearchEntity(
    val metaResponse: MetaEntity,
    val documents: List<ImageDocumentsEntity>,
)
