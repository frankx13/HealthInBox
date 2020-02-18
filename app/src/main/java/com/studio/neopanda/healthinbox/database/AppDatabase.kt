package com.studio.neopanda.healthinbox.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Aliments::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alimentsDao(): AlimentDAO

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "aliments_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }

        class PopulateDbAsyncTask(db: AppDatabase?) : AsyncTask<Unit, Unit, Unit>() {
            private val alimentsDao = db?.alimentsDao()

            override fun doInBackground(vararg p0: Unit?) {
                alimentsDao?.insert((Aliments("Bagel", 140)))
                alimentsDao?.insert((Aliments("Biscuit digestives", 86)))
                alimentsDao?.insert((Aliments("Bread white", 96)))
                alimentsDao?.insert((Aliments("Bread wholemeal", 88)))
                alimentsDao?.insert((Aliments("Chapatis", 275)))
                alimentsDao?.insert((Aliments("Cornflakes", 130)))
                alimentsDao?.insert((Aliments("Crackerbread", 17)))
                alimentsDao?.insert((Aliments("Cream crackers", 35)))
                alimentsDao?.insert((Aliments("Crumpets", 140)))
                alimentsDao?.insert((Aliments( "Jaffa cake", 48)))
                alimentsDao?.insert((Aliments( "Flapjacks basic fruit mix", 320)))
                alimentsDao?.insert((Aliments( "Macaroni (boiled)", 238)))
                alimentsDao?.insert((Aliments( "Muesli", 195)))
                alimentsDao?.insert((Aliments( "Naan bread (normal)", 300)))
                alimentsDao?.insert((Aliments( "Noodles (boiled)", 175)))
                alimentsDao?.insert((Aliments( "Pasta (normal boiled)", 330)))
                alimentsDao?.insert((Aliments( "Pasta (wholemeal boiled)", 315)))
                alimentsDao?.insert((Aliments( "Porridge oats (with water)", 193)))
                alimentsDao?.insert((Aliments( "Potatoes (boiled)", 210)))
                alimentsDao?.insert((Aliments( "Potatoes (roast)", 420)))
                alimentsDao?.insert((Aliments( "Rice (white boiled)", 420)))
                alimentsDao?.insert((Aliments( "Rice (egg-fried)", 500)))
                alimentsDao?.insert((Aliments( "Rice (brown)", 405)))
                alimentsDao?.insert((Aliments( "Rice cakes", 28)))
                alimentsDao?.insert((Aliments( "Ryvita Multi grain", 37)))
                alimentsDao?.insert((Aliments( "Ryvita Seeds & Oats", 180)))
                alimentsDao?.insert((Aliments( "Bagel", 140)))
                alimentsDao?.insert((Aliments( "Spaghetti (boiled)", 303)))
            }
        }
    }
}