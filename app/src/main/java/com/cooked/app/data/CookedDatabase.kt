package com.cooked.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameSession::class], version = 1, exportSchema = false)
abstract class CookedDatabase : RoomDatabase() {
    abstract fun sessionDao(): GameSessionDao

    companion object {
        @Volatile private var INSTANCE: CookedDatabase? = null
        fun get(context: Context): CookedDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CookedDatabase::class.java,
                    "cooked.db"
                ).build().also { INSTANCE = it }
            }
    }
}
