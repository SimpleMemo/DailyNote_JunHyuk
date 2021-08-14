package com.junhyuk.dailynote.di.room

import com.junhyuk.dailynote.model.database.MemoDao
import com.junhyuk.dailynote.model.repository.MemoRepository
import com.junhyuk.dailynote.paging.MemoPageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryDI {

    @Provides
    @Singleton
    fun provideRepository(memoDao: MemoDao): MemoRepository {
        return MemoRepository(memoDao)
    }

    @Provides
    @Singleton
    fun providePagedRepository(memoDao: MemoDao): MemoPageRepository {
        return MemoPageRepository(memoDao)
    }

}