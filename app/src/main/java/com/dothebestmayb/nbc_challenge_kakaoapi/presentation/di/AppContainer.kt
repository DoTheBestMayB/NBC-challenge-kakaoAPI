package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di

import android.content.Context
import androidx.room.Room
import com.dothebestmayb.nbc_challenge_kakaoapi.data.database.AppDatabase
import com.dothebestmayb.nbc_challenge_kakaoapi.data.database.ImageDocumentDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource.KakaoRemoteDataSourceImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.KakaoService
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.RetrofitClient
import com.dothebestmayb.nbc_challenge_kakaoapi.data.repository.BookmarkRepositoryImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCaseImpl
import retrofit2.Retrofit

class AppContainer(private val context: Context) {
    fun createRetrofit(): Retrofit {
        return RetrofitClient.retrofitClient
    }

    fun createKakaoService(): KakaoService {
        return createRetrofit().create(KakaoService::class.java)
    }

    fun createKakaoRemoteDataSource(): KakaoRemoteDataSource {
        return KakaoRemoteDataSourceImpl(createKakaoService())
    }

    fun createImageDocumentDao(): ImageDocumentDao {
        return db.imageDocumentDao()
    }

    fun createBookmarkRepository(): BookmarkRepository {
        return BookmarkRepositoryImpl(
            createImageDocumentDao()
        )
    }

    fun createCheckMediaIsBookmarkedUseCase(): CheckMediaIsBookmarkedUseCase {
        return CheckMediaIsBookmarkedUseCaseImpl(
            createBookmarkRepository()
        )
    }

    fun createDeleteBookmarkedImageUseCase(): DeleteBookmarkedImageUseCase {
        return DeleteBookmarkedImageUseCaseImpl(
            createBookmarkRepository()
        )
    }

    fun createGetAllBookmarkedImageUseCase(): GetAllBookmarkedImageUseCase {
        return GetAllBookmarkedImageUseCaseImpl(
            createBookmarkRepository()
        )
    }

    fun createInsertBookmarkedImageUseCase(): InsertBookmarkedImageUseCase {
        return InsertBookmarkedImageUseCaseImpl(
            createBookmarkRepository()
        )
    }

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "database-dodobest")
            .build()
    }

    var searchContainer: SearchContainer? = null
    var bookmarkContainer: BookmarkContainer? = null
}
