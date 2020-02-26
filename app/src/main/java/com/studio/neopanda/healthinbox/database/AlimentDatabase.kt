package com.studio.neopanda.healthinbox.database

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [Aliment::class, Recipe::class, Meal::class],
    version = 1,
    exportSchema = false
)
abstract class AlimentDatabase : RoomDatabase() {

    abstract fun alimentDao(): AlimentDao
    abstract fun recipeDao(): RecipeDao
    abstract fun mealDao(): MealDao


    companion object {
        private var instance: AlimentDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AlimentDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
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
                super.onCreate(db)

                Log.e("DBMANAGEMENT", "DATABASE ONCREATE")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                PopulateDBAsyncTask(instance!!).execute()
                Log.e("DBMANAGEMENT", "DATABASE ONOPEN")
            }
        }

        private class PopulateDBAsyncTask constructor(db: AlimentDatabase) :
            AsyncTask<Void, Void, Void>() {

            private val alimentDao: AlimentDao = db.alimentDao()
            private val mealDao: MealDao = db.mealDao()
            private val recipeDao: RecipeDao = db.recipeDao()

            override fun doInBackground(vararg voids: Void): Void? {
                alimentDao.insert(Aliment("PineApple (medium)", 50, 112))
                alimentDao.insert(Aliment("Banana (large)", 110, 126))
                alimentDao.insert(Aliment("Apple (large)", 130, 142))
                alimentDao.insert(Aliment("Avocado (medium)", 50, 30))

                mealDao.insert(Meal("Fruit Salad", "22-02-2020", 370))
                mealDao.insert(Meal("Banana Apple", "22-02-2020", 270))
                mealDao.insert(Meal("Avocado PineApple", "22-02-2020", 100))

                recipeDao.insert(Recipe("Couscous", "3 tomatoes, 3 peppers, couscous thin grain, chicken, beef, veal, paprika, harissa", "Make mijoter tout ca un moment", 10, 2, 50, 200))

                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

            }
        }
    }
}