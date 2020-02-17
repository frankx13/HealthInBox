package com.studio.neopanda.healthinbox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AppDatabase
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    var alimentsList: ArrayList<Aliment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dropBoxBtn.setOnClickListener {
            val intent = Intent(this, LoginDropBoxActivity::class.java)
            startActivity(intent)
            finish()
        }

        sportsBtn.setOnClickListener {
            val intent = Intent(this, SportManagerActivity::class.java)
            startActivity(intent)
            finish()
        }

        foodBtn.setOnClickListener {
            val intent = Intent(this, FoodManagerActivity::class.java)
            startActivity(intent)
            finish()
        }

        initAlimentsData()
        fillAlimentsTable()
    }

    private fun initAlimentsData() {
        alimentsList = ArrayList()
        alimentsList!!.add(Aliment(1, "Bagel", 140))
        alimentsList!!.add(Aliment(2, "Biscuit digestives", 86))
        alimentsList!!.add(Aliment(3, "Bread white", 96))
        alimentsList!!.add(Aliment(4, "Bread wholemeal", 88))
        alimentsList!!.add(Aliment(5, "Chapatis", 275))
        alimentsList!!.add(Aliment(6, "Cornflakes", 130))
        alimentsList!!.add(Aliment(7, "Crackerbread", 17))
        alimentsList!!.add(Aliment(8, "Cream crackers", 35))
        alimentsList!!.add(Aliment(9, "Crumpets", 140))
        alimentsList!!.add(Aliment(10, "Jaffa cake", 48))
        alimentsList!!.add(Aliment(11, "Flapjacks basic fruit mix", 320))
        alimentsList!!.add(Aliment(12, "Macaroni (boiled)", 238))
        alimentsList!!.add(Aliment(13, "Muesli", 195))
        alimentsList!!.add(Aliment(14, "Naan bread (normal)", 300))
        alimentsList!!.add(Aliment(15, "Noodles (boiled)", 175))
        alimentsList!!.add(Aliment(16, "Pasta (normal boiled)", 330))
        alimentsList!!.add(Aliment(17, "Pasta (wholemeal boiled)", 315))
        alimentsList!!.add(Aliment(18, "Porridge oats (with water)", 193))
        alimentsList!!.add(Aliment(19, "Potatoes (boiled)", 210))
        alimentsList!!.add(Aliment(20, "Potatoes (roast)", 420))
        alimentsList!!.add(Aliment(21, "Rice (white boiled)", 420))
        alimentsList!!.add(Aliment(22, "Rice (egg-fried)", 500))
        alimentsList!!.add(Aliment(23, "Rice (brown)", 405))
        alimentsList!!.add(Aliment(24, "Rice cakes", 28))
        alimentsList!!.add(Aliment(25, "Ryvita Multi grain", 37))
        alimentsList!!.add(Aliment(26, "Ryvita Seeds & Oats", 180))
        alimentsList!!.add(Aliment(27, "Bagel", 140))
        alimentsList!!.add(Aliment(28, "Spaghetti (boiled)", 303))
    }

    private fun fillAlimentsTable() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "HealthInBoxDB.db"
        ).build()
        if (alimentsList!!.isNotEmpty()) {
            for (aliment in 0 until alimentsList!!.size)
                db.alimentDao().insertAll()
        }
    }
}
