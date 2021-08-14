package com.junhyuk.dailynote.di.factory

import com.junhyuk.dailynote.model.repository.MemoRepository
import com.junhyuk.dailynote.paging.MemoPageRepository
import com.junhyuk.dailynote.viewmodel.main.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        memoPageRepository: MemoPageRepository,
        memoRepository: MemoRepository
    ): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(
            memoPageRepository,
            memoRepository
        )
    }

}