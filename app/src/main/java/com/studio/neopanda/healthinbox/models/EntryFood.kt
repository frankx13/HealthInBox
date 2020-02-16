package com.studio.neopanda.healthinbox.models

class EntryFood (foodName : String, foodCalories : Int, foodDate : String) {

    var foodName: String = ""
    var foodCalories: Int = 0
    var foodDate: String = ""

    init {
        this.foodName = foodName
        this.foodCalories = foodCalories
        this.foodDate = foodDate
    }
}