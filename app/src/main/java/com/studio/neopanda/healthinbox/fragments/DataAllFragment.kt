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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.AddEditAlimentActivity
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.AlimentAdapter
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AlimentViewModel
import kotlinx.android.synthetic.main.fragment_data_all.*
import kotlinx.android.synthetic.main.fragment_food_data.*

class DataAllFragment : Fragment() {

    private var alimentViewModel: AlimentViewModel? = null
    private val ADD_ALIMENT_REQUEST = 1
    private val EDIT_ALIMENT_REQUEST = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExitFab()

        //Init RV
        val recyclerView = recyclerview_all
        recyclerView.layoutManager =
            LinearLayoutManager(parentFragment!!.activity!!.applicationContext)
        recyclerView.setHasFixedSize(true)

        //Init RV Adapter
        val adapter = AlimentAdapter()
        recyclerView.adapter = adapter

        //OBSERVE ALIMENTS
        alimentViewModel = ViewModelProviders.of(this).get(AlimentViewModel::class.java)
        alimentViewModel!!.getAllAliments().observe(
            this,
            Observer<List<Aliment>>(fun(aliments: List<Aliment>) {
                aliments.sortedBy { it.name }
                adapter.submitList(null)
                adapter.notifyDataSetChanged()
                adapter.submitList(aliments) //even though aliments exist, they are not displayed unless we add something (= until the observer notify a change)
                adapter.notifyDataSetChanged()
                recyclerView.smoothScrollToPosition(adapter.itemCount)

                Toast.makeText(parentFragment!!.activity!!.applicationContext, "OBSERVING", Toast.LENGTH_SHORT)
                    .show()
            })
        )

        //EDIT ONE ALIMENT
        adapter.setOnItemClickListener(object : AlimentAdapter.OnItemClickListener {
            override fun onItemClick(aliment: Aliment) {
                val intent = Intent(parentFragment!!.activity, AddEditAlimentActivity::class.java)

                intent.putExtra(AddEditAlimentActivity.EXTRA_ID, aliment.id)
                intent.putExtra(AddEditAlimentActivity.EXTRA_NAME, aliment.name)
                intent.putExtra(AddEditAlimentActivity.EXTRA_CALORIES, aliment.calories)
                intent.putExtra(AddEditAlimentActivity.EXTRA_WEIGHT, aliment.weight)

                startActivityForResult(intent, EDIT_ALIMENT_REQUEST)
            }
        })

        //ADD ONE ALIMENT
        val fabAddAliment = fab_add_aliment_all
        fabAddAliment.setOnClickListener {
            val intent = Intent(parentFragment!!.activity, AddEditAlimentActivity::class.java)
            startActivityForResult(intent, ADD_ALIMENT_REQUEST)
        }

        //REMOVE ONE ALIMENT
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
                alimentViewModel!!.delete(adapter.getAlimentAt(viewHolder.adapterPosition))
                Toast.makeText(parentFragment!!.activity, "Aliment deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)

        //REMOVE ALL ALIMENTS
        val fabDeleteAllAliments = fab_delete_all_aliments_all
        fabDeleteAllAliments.setOnClickListener {
            alimentViewModel!!.deleteAllAliments()
            Toast.makeText(parentFragment!!.activity, "Aliments nuked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_ALIMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            val name = data!!.getStringExtra(AddEditAlimentActivity.EXTRA_NAME)
            val calories = data.getIntExtra(AddEditAlimentActivity.EXTRA_CALORIES, 1)
            val weight = data.getIntExtra(AddEditAlimentActivity.EXTRA_WEIGHT, 1)

            val aliment = Aliment(name!!, calories, weight)
            alimentViewModel!!.insert(aliment)

            Toast.makeText(parentFragment!!.activity, "Aliment saved", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_ALIMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data!!.getIntExtra(AddEditAlimentActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(parentFragment!!.activity, "Aliment cannot be saved", Toast.LENGTH_SHORT).show()
                return
            }

            val name = data.getStringExtra(AddEditAlimentActivity.EXTRA_NAME)
            val calories = data.getIntExtra(AddEditAlimentActivity.EXTRA_CALORIES, 1)
            val weight = data.getIntExtra(AddEditAlimentActivity.EXTRA_WEIGHT, 1)

            val aliment = Aliment(name!!, calories, weight)
            aliment.id = id
            alimentViewModel!!.update(aliment)

            Toast.makeText(parentFragment!!.activity, "Aliment updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(parentFragment!!.activity, "Aliment not updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setExitFab() {
        fab_exit_food_data_all.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
