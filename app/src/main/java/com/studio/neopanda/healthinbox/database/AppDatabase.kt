package com.studio.neopanda.healthinbox.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Aliment::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun alimentDao(): AlimentDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "HealthInBoxDB.db")
            .build()
    }
    /*
    // Use this code to get an instance of the created database :
    ***--------------------------------------------------***
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()
    ***--------------------------------------------------***
    */
}