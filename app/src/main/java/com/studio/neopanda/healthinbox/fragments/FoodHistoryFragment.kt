package com.studio.neopanda.healthinbox.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.AddEditMealActivity
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.MealsAdapter
import com.studio.neopanda.healthinbox.database.Meal
import com.studio.neopanda.healthinbox.database.MealViewModel
import kotlinx.android.synthetic.main.fragment_food_history.*

class FoodHistoryFragment : Fragment() {
    private var mealViewModel: MealViewModel? = null
    private val ADD_MEAL_REQUEST = 1
    private val EDIT_MEAL_REQUEST = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExitFab()

        val recyclerView = recyclerview_meal
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.setHasFixedSize(true)

        val adapter = MealsAdapter()
        recyclerView.adapter = adapter

        //OBSERVE MEALS
        mealViewModel = ViewModelProviders.of(this).get(MealViewModel::class.java)
        mealViewModel!!.getAllMeals().observe(
            this,
            Observer<List<Meal>>(fun(meals: List<Meal>) {
                adapter.submitList(null)
                adapter.notifyDataSetChanged()
                adapter.submitList(meals)
                adapter.notifyDataSetChanged()
                Log.e("ASYNCTASK STATUS", "LOAD RV")
            })
        )

        //EDIT ONE MEAL
        adapter.setOnItemClickListener(object : MealsAdapter.OnItemClickListener {
            override fun onItemClick(meal: Meal) {
                val intent = Intent(activity, AddEditMealActivity::class.java)

                intent.putExtra(AddEditMealActivity.EXTRA_ID, meal.id)
                intent.putExtra(AddEditMealActivity.EXTRA_NAME, meal.name)
                intent.putExtra(AddEditMealActivity.EXTRA_DATE, meal.date)
                intent.putExtra(AddEditMealActivity.EXTRA_CALORIES, meal.calories)

                startActivityForResult(intent, EDIT_MEAL_REQUEST)
            }
        })

        //ADD ONE MEAL
        val fabAddMeal = fab_add_meal
        fabAddMeal.setOnClickListener {
            val intent = Intent(activity, AddEditMealActivity::class.java)
            startActivityForResult(intent, ADD_MEAL_REQUEST)
        }

        //REMOVE ALL ALIMENTS
        val fabDeleteAllMeals = fab_delete_all_meal
        fabDeleteAllMeals.setOnClickListener {
            mealViewModel!!.deleteAllMeals()
            Toast.makeText(activity, "Meals nuked", Toast.LENGTH_SHORT).show()
        }

        //REMOVE ONE MEAL
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mealViewModel!!.delete(adapter.getMealAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Meal deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_MEAL_REQUEST && resultCode == Activity.RESULT_OK) {
            val name = data!!.getStringExtra(AddEditMealActivity.EXTRA_NAME)
            val date = data.getStringExtra(AddEditMealActivity.EXTRA_DATE)
            val calories = data.getIntExtra(AddEditMealActivity.EXTRA_CALORIES, 1)

            val meal = Meal(name!!, date!!, calories)
            mealViewModel!!.insert(meal)

            Toast.makeText(activity, "Meal saved", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_MEAL_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data!!.getIntExtra(AddEditMealActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(activity, "Meal cannot be saved", Toast.LENGTH_SHORT).show()
                return
            }

            val name = data.getStringExtra(AddEditMealActivity.EXTRA_NAME)
            val date = data.getStringExtra(AddEditMealActivity.EXTRA_DATE)
            val calories = data.getIntExtra(AddEditMealActivity.EXTRA_CALORIES, 1)

            val meal = Meal(name!!, date!!, calories)
            meal.id = id
            mealViewModel!!.update(meal)

            Toast.makeText(activity, "Meal updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Meal not updated", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setExitFab() {
        fab_exit_food_history.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
