package com.vincularte.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vincularte.data.model.DiarioEmocional
import com.vincularte.data.repository.DiarioDao

@Database(entities = [DiarioEmocional::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diarioDao(): DiarioDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vincularte_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
