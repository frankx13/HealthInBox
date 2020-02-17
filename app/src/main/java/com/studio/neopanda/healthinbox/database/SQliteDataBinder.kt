package com.studio.neopanda.healthinbox.database

import android.provider.BaseColumns



class SQliteDataBinder {

    class DataFoodAliments : BaseColumns {
        companion object {
            val TABLE_NAME = "Aliments"
            val COLUMN_ALIMENT_NAME = "AlimentName"
            val COLUMN_ALIMENT_CALORIES = "AlimentCalories"
            val COLUMN_ALIMENT_DATA_ID = "AlimentID"
        }
    }
}