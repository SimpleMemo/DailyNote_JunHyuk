package com.junhyuk.dailynote.di.adapter

import com.junhyuk.dailynote.adapter.MemoRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterDi {

    @Provides
    @Singleton
    fun provideAdapter(): MemoRecyclerViewAdapter{
        return MemoRecyclerViewAdapter()
    }

}