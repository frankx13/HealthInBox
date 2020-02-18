package com.studio.neopanda.healthinbox.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Aliment::class], version = 3)
abstract class AlimentDatabase : RoomDatabase() {

    abstract fun alimentDao(): AlimentDao

    companion object {
        private var instance: AlimentDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AlimentDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AlimentDatabase::class.java, "aliments_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }

            return instance as AlimentDatabase
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                instance?.let { PopulateDBAsyncTask(it).execute() }

                super.onCreate(db)
            }
        }

         private class PopulateDBAsyncTask constructor(db: AlimentDatabase) :
            AsyncTask<Void, Void, Void>() {

            private val alimentDao: AlimentDao = db.alimentDao()

            override fun doInBackground(vararg voids: Void): Void? {
                alimentDao.insert(Aliment("PineApple (medium)", 50, 112))
                alimentDao.insert(Aliment("Banana (large)", 110, 126))
                alimentDao.insert(Aliment("Apple (large)", 130, 142))
                alimentDao.insert(Aliment("Avocado (medium)", 50, 30))

                return null
            }
        }
    }
}