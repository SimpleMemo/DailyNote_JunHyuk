package com.junhyuk.dailynote.di.room

import android.app.Application
import androidx.room.Room
import com.junhyuk.dailynote.model.database.MemoDao
import com.junhyuk.dailynote.model.database.MemoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDi {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): MemoDataBase{
        return Room.databaseBuilder(context, MemoDataBase::class.java, "diary_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideService(dataBase: MemoDataBase): MemoDao{
        return dataBase.memoDao()
    }

}