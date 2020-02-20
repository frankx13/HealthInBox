package com.studio.neopanda.healthinbox.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.studio.neopanda.healthinbox.AddEditAlimentActivity
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.AlimentAdapter
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AlimentViewModel
import kotlinx.android.synthetic.main.fragment_food_data.*


class FoodDataFragment : Fragment() {
    private var alimentViewModel: AlimentViewModel? = null
    private val ADD_ALIMENT_REQUEST = 1
    private val EDIT_ALIMENT_REQUEST = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.setHasFixedSize(true)

        val adapter = AlimentAdapter()
        recyclerView.adapter = adapter

        alimentViewModel = ViewModelProviders.of(this).get(AlimentViewModel::class.java)
        alimentViewModel!!.getAllAliments().observe(
            this,
            Observer<List<Aliment>>(fun(aliments: List<Aliment>) {
                adapter.submitList(aliments)
                Toast.makeText(activity!!.applicationContext, "OBSERVING", Toast.LENGTH_SHORT)
                    .show()
            })
        )

        adapter.setOnItemClickListener(object : AlimentAdapter.OnItemClickListener {
            override fun onItemClick(aliment: Aliment) {
                val intent = Intent(activity, AddEditAlimentActivity::class.java)

                intent.putExtra(AddEditAlimentActivity.EXTRA_ID, aliment.id)
                intent.putExtra(AddEditAlimentActivity.EXTRA_NAME, aliment.name)
                intent.putExtra(AddEditAlimentActivity.EXTRA_CALORIES, aliment.calories)
                intent.putExtra(AddEditAlimentActivity.EXTRA_WEIGHT, aliment.weight)

                startActivityForResult(intent, EDIT_ALIMENT_REQUEST)
            }
        })

        val fabAddNote = fab_add_aliment
        fabAddNote.setOnClickListener {
            val intent = Intent(activity, AddEditAlimentActivity::class.java)
            startActivityForResult(intent, ADD_ALIMENT_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_ALIMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            val name = data!!.getStringExtra(AddEditAlimentActivity.EXTRA_NAME)
            val calories = data.getIntExtra(AddEditAlimentActivity.EXTRA_CALORIES, 1)
            val weight = data.getIntExtra(AddEditAlimentActivity.EXTRA_WEIGHT, 1)

            val note = Aliment(name!!, calories, weight)
            alimentViewModel!!.insert(note)

            Toast.makeText(activity, "Aliment saved", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_ALIMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data!!.getIntExtra(AddEditAlimentActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(activity, "Aliment cannot be saved", Toast.LENGTH_SHORT).show()
                return
            }

            val name = data.getStringExtra(AddEditAlimentActivity.EXTRA_NAME)
            val calories = data.getIntExtra(AddEditAlimentActivity.EXTRA_CALORIES, 1)
            val weight = data.getIntExtra(AddEditAlimentActivity.EXTRA_WEIGHT, 1)

            val aliment = Aliment(name!!, calories, weight)
            aliment.id = id
            alimentViewModel!!.update(aliment)

            Toast.makeText(activity, "Aliment updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Aliment not updated", Toast.LENGTH_SHORT).show()
        }
    }
}
