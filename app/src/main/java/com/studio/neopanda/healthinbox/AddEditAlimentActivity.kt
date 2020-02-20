package com.studio.neopanda.healthinbox

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_edit_aliment.*


class AddEditAlimentActivity : AppCompatActivity() {

    private var editTextName: EditText? = null
    private var editTextCalories: EditText? = null
    private var editTextWeight: EditText? = null
    private var titleScreen: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_aliment)

        editTextName = findViewById(R.id.edit_text_name)
        editTextCalories = findViewById(R.id.edit_text_calories)
        editTextWeight = findViewById(R.id.edit_text_weight)

        val intent = intent

        if (intent.hasExtra(EXTRA_ID)) {
            titleScreen = "Edit Note"
            editTextName!!.setText(intent.getStringExtra(EXTRA_NAME))
            editTextCalories!!.setText("" + intent.getIntExtra(EXTRA_CALORIES, 1))
            editTextWeight!!.setText("" + intent.getIntExtra(EXTRA_WEIGHT, 1))
        } else {
            titleScreen = "Add Note"
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
        val calories = editTextCalories!!.text.toString()
        val weight = editTextWeight!!.text.toString()

        if (name.trim { it <= ' ' }.isEmpty() ||
            calories.trim { it <= ' '}.isEmpty() ||
            weight.trim { it <= ' '}.isEmpty()
        ) {
            Toast.makeText(
                this,
                "You must fill both the name, calories and weight to add an aliment!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_NAME, name)
        data.putExtra(EXTRA_CALORIES, calories.toInt())
        data.putExtra(EXTRA_WEIGHT, weight.toInt())

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_NAME = "com.studio.neopanda.healthinbox.EXTRA_NAME"
        const val EXTRA_CALORIES = "com.studio.neopanda.healthinbox.EXTRA_CALORIES"
        const val EXTRA_WEIGHT = "com.studio.neopanda.healthinbox.EXTRA_WEIGHT"
        const val EXTRA_ID = "com.studio.neopanda.healthinbox.EXTRA_ID"
    }
}