package com.dicoding.asclepius.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.dao.HistoryDao
import com.dicoding.asclepius.data.local.entity.HistoryEntity


@Database(entities = [HistoryEntity::class], version = 3, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null
        fun getInstance(context: Context): HistoryDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "HistoryDB"
                ).fallbackToDestructiveMigration().build()
            }
    }

}