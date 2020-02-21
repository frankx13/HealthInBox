package com.studio.neopanda.healthinbox

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_edit_recipe.*

class AddEditRecipeActivity : AppCompatActivity() {

    private var editTextName: EditText? = null
    private var editTextPeople: EditText? = null
    private var editTextTime: EditText? = null
    private var editTextCost: EditText? = null
    private var editTextDifficulty: EditText? = null
    private var editTextIngredients: EditText? = null
    private var editTextInstructions: EditText? = null
    private var titleScreen: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_recipe)

        editTextName = findViewById(R.id.et_addedit_recipe_name)
        editTextPeople = findViewById(R.id.et_addedit_recipe_people)
        editTextTime = findViewById(R.id.et_addedit_recipe_time)
        editTextCost = findViewById(R.id.et_addedit_recipe_cost)
        editTextDifficulty = findViewById(R.id.et_addedit_recipe_difficulty)
        editTextIngredients = findViewById(R.id.et_addedit_recipe_ingredients)
        editTextInstructions = findViewById(R.id.et_addedit_recipe_instructions)

        val intent = intent

        if (intent.hasExtra(EXTRA_ID)) {
            titleScreen = "Edit Recipe"
            editTextName!!.setText(intent.getStringExtra(EXTRA_NAME))
            editTextPeople!!.setText("" + intent.getIntExtra(EXTRA_PEOPLE, 1))
            editTextTime!!.setText("" + intent.getIntExtra(EXTRA_TIME, 1))
            editTextCost!!.setText("" + intent.getIntExtra(EXTRA_COST, 1))
            editTextDifficulty!!.setText("" + intent.getIntExtra(EXTRA_DIFFICULTY, 1))
            editTextIngredients!!.setText("" + intent.getStringExtra(EXTRA_INGREDIENTS))
            editTextInstructions!!.setText("" + intent.getStringExtra(EXTRA_INSTRUCTIONS))
        } else {
            titleScreen = "Add Recipe"
        }

        val fabAddRecipe: FloatingActionButton = fab_save_addedit_recipe
        fabAddRecipe.setOnClickListener {
            saveRecipe()
        }

        val fabCancelOperation: FloatingActionButton = fab_cancel_addedit_recipe
        fabCancelOperation.setOnClickListener {
            finish()
        }
    }

    private fun saveRecipe() {
        val name = editTextName!!.text.toString()
        val people = editTextPeople!!.text.toString()
        val time = editTextTime!!.text.toString()
        val cost = editTextCost!!.text.toString()
        val difficulty = editTextDifficulty!!.text.toString()
        val ingredients = editTextIngredients!!.text.toString()
        val instructions = editTextInstructions!!.text.toString()

        if (name.trim { it <= ' ' }.isEmpty() ||
            people.trim { it <= ' '}.isEmpty() ||
            time.trim { it <= ' '}.isEmpty() ||
            cost.trim { it <= ' '}.isEmpty() ||
            difficulty.trim { it <= ' '}.isEmpty() ||
            ingredients.trim { it <= ' '}.isEmpty() ||
            instructions.trim { it <= ' '}.isEmpty()
        ) {
            Toast.makeText(
                this,
                "You must fill all the fields to continue!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_NAME, name)
        data.putExtra(EXTRA_PEOPLE, people.toInt())
        data.putExtra(EXTRA_TIME, time.toInt())
        data.putExtra(EXTRA_COST, cost.toInt())
        data.putExtra(EXTRA_DIFFICULTY, difficulty.toInt())
        data.putExtra(EXTRA_INGREDIENTS, ingredients)
        data.putExtra(EXTRA_INSTRUCTIONS, instructions)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_NAME = "com.studio.neopanda.healthinbox.EXTRA_NAME"
        const val EXTRA_PEOPLE = "com.studio.neopanda.healthinbox.EXTRA_PEOPLE"
        const val EXTRA_TIME = "com.studio.neopanda.healthinbox.EXTRA_TIME"
        const val EXTRA_COST = "com.studio.neopanda.healthinbox.EXTRA_COST"
        const val EXTRA_DIFFICULTY = "com.studio.neopanda.healthinbox.EXTRA_DIFFICULTY"
        const val EXTRA_INGREDIENTS = "com.studio.neopanda.healthinbox.EXTRA_INGREDIENTS"
        const val EXTRA_INSTRUCTIONS = "com.studio.neopanda.healthinbox.EXTRA_INSTRUCTIONS"
        const val EXTRA_ID = "com.studio.neopanda.healthinbox.EXTRA_ID"
    }
}
