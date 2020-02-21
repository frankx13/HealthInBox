package com.studio.neopanda.healthinbox.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Aliment::class, Recipe::class], version = 6, exportSchema = false)
abstract class AlimentDatabase : RoomDatabase() {

    abstract fun alimentDao(): AlimentDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        private var instance: AlimentDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AlimentDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlimentDatabase::class.java, "aliments_database"
                )
                    .fallbackToDestructiveMigration() // not calling AsyncTask
//                    .addCallback(roomCallback)
                    .build()
            }

            return instance as AlimentDatabase
        }

//        private val roomCallback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                PopulateDBAsyncTask(instance!!).execute()
//
//                super.onCreate(db)
//            }
//        }
//
//        private class PopulateDBAsyncTask constructor(db: AlimentDatabase) :
//            AsyncTask<Void, Void, Void>() {
//
//            private val alimentDao: AlimentDao = db.alimentDao()
//
//            override fun doInBackground(vararg voids: Void): Void? {
//                alimentDao.insert(Aliment("PineApple (medium)", 50, 112))
//                alimentDao.insert(Aliment("Banana (large)", 110, 126))
//                alimentDao.insert(Aliment("Apple (large)", 130, 142))
//                alimentDao.insert(Aliment("Avocado (medium)", 50, 30))
//
//                return null
//            }
//        }
    }
}