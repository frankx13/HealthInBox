package com.studio.neopanda.healthinbox

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_edit_aliment.*

class AddEditMealActivity : AppCompatActivity() {

    private var editTextName: EditText? = null
    private var editTextDate: EditText? = null
    private var editTextCalories: EditText? = null
    private var titleScreen: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_meal)

        editTextName = findViewById(R.id.edit_text_meal_name)
        editTextDate = findViewById(R.id.edit_text_meal_date)
        editTextCalories = findViewById(R.id.edit_text_meal_calories)

        val intent = intent

        if (intent.hasExtra(EXTRA_ID)) {
            titleScreen = "Edit Meal"
            editTextName!!.setText(intent.getStringExtra(EXTRA_NAME))
            editTextDate!!.setText(intent.getStringExtra(EXTRA_DATE))
            editTextCalories!!.setText("" + intent.getIntExtra(EXTRA_CALORIES, 1))
        } else {
            titleScreen = "Add Meal"
        }

        val fabAddAliment: FloatingActionButton = fab_save_add_edit
        fabAddAliment.setOnClickListener {
            saveAliment()
        }

        val fabCancelOperation: FloatingActionButton = fab_cancel_add_edit
        fabCancelOperation.setOnClickListener {
            finish()
        }
    }

    private fun saveAliment() {
        val name = editTextName!!.text.toString()
        val date = editTextDate!!.text.toString()
        val calories = editTextCalories!!.text.toString()

        if (name.trim { it <= ' ' }.isEmpty() ||
            date.trim { it <= ' '}.isEmpty() ||
            calories.trim { it <= ' '}.isEmpty()
        ) {
            Toast.makeText(
                this,
                "You must fill the name, date and calories to add a meal!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_NAME, name)
        data.putExtra(EXTRA_DATE, date)
        data.putExtra(EXTRA_CALORIES, calories.toInt())

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_NAME = "com.studio.neopanda.healthinbox.EXTRA_NAME"
        const val EXTRA_DATE = "com.studio.neopanda.healthinbox.EXTRA_DATE"
        const val EXTRA_CALORIES = "com.studio.neopanda.healthinbox.EXTRA_CALORIES"
        const val EXTRA_ID = "com.studio.neopanda.healthinbox.EXTRA_ID"
    }
}