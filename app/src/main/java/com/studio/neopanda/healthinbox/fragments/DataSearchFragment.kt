package com.studio.neopanda.healthinbox.fragments


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.adapters.DataSearchAdapter
import com.studio.neopanda.healthinbox.database.entities.Aliment
import com.studio.neopanda.healthinbox.database.viewmodels.AlimentViewModel
import kotlinx.android.synthetic.main.fragment_data_search.*

class DataSearchFragment : Fragment() {

    private var alimentNameInputToStock: String = ""
    private var alimentCaloriesInput = 0
    private var alimentWeight = 100
    private var alimentViewModel: AlimentViewModel? = null
    private var isAlimentFound: Boolean = false
    private var userAnswer = 0

    private lateinit var recyclerViewSearchFound: RecyclerView

    private lateinit var alimentsSearched: ArrayList<Aliment>

    private var alimentsNamesList: ArrayList<String>? = null
    private var alimentsCaloriesList: ArrayList<Int>? = null

    private lateinit var manager: LocalBroadcastManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBroadCastReceiver()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExitFab()
        alimentViewModel = ViewModelProviders.of(this).get(AlimentViewModel::class.java)
        validateUserInput()
        manageInputUnknownAliment()
        manageAddUnknownAliment()
        initRV()
    }

    private fun initRV() {
        recyclerViewSearchFound = recyclerview_search_aliment_found
        recyclerViewSearchFound.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerViewSearchFound.setHasFixedSize(true)
    }

    //USER WANTS TO ADD THE UNKNOWN ALIMENT
    private fun manageAddUnknownAliment() {
        btn_next_validate.setOnClickListener {
            if (et_aliment_search_prompt.editableText.toString().trim().isNotEmpty()) {
                alimentCaloriesInput = et_aliment_search_prompt.editableText.toString().toInt()
                alimentViewModel!!.insert(
                    Aliment(
                        alimentNameInputToStock,
                        alimentCaloriesInput,
                        alimentWeight
                    )
                )
                Toast.makeText(
                    parentFragment!!.activity!!.applicationContext,
                    "The $alimentNameInputToStock has been added to your database!",
                    Toast.LENGTH_LONG
                ).show()
                container_add_aliment_search.visibility = View.GONE
            }
        }
    }

    private fun manageInputUnknownAliment() {
        //USER WANTS TO ADD THE ALIMENT
        userAnswer = 0
        btn_add_unknown_aliment.setOnClickListener {
            container_no_result_prompts.visibility = View.GONE
            tv_aliment_search_result.visibility = View.GONE
            userAnswer = 2
            inputUnknownAliment(userAnswer)
        }
        //USER WANTS TO IGNORE THE ALIMENT
        btn_ignore_unknown_aliment.setOnClickListener {
            container_no_result_prompts.visibility = View.GONE
            tv_aliment_search_result.visibility = View.GONE
            userAnswer = 1
            inputUnknownAliment(userAnswer)
        }
    }

    //USER IS LAUNCHING THE RESEARCH
    private fun validateUserInput() {
        clearSearchParams()
        btn_validate_aliment_search_input.setOnClickListener {
            alimentNameInputToStock =
                et_search_aliment_input.editableText.toString().trim().capitalize()

            alimentViewModel!!.searchAliments(alimentNameInputToStock, parentFragment!!.activity!!)
        }
    }


    private fun inputUnknownAliment(userInputAnswer: Int) {
        //USER WANTS TO ADD THE UNKNOWN ALIMENT
        if (userInputAnswer == 2) {
            container_add_aliment_search.visibility = View.VISIBLE
        } else { //USER WANTS TO IGNORE THE UNKNOWN ALIMENT
            userAnswer = 0
        }
    }

    //INIT BR
    private fun initBroadCastReceiver() {
        manager = LocalBroadcastManager.getInstance(context!!)
        val receiver = MyBroadCastReceiver()
        val filter = IntentFilter()
        filter.addAction("com.action.search")
        manager.registerReceiver(receiver, filter)
    }

    //RECEIVE BROADCAST MESSAGE FROM REPOSITORY ASYNCTASK
    internal inner class MyBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            alimentsNamesList = intent.getStringArrayListExtra("keyAlimentsNames")
            alimentsCaloriesList = intent.getIntegerArrayListExtra("keyAlimentsCalories")
            Log.e("BROADCAST RECEIVED", "AlimNamesList : " + alimentsNamesList.toString())
            Log.e("BROADCAST RECEIVED", "AlimCalsList : " + alimentsCaloriesList.toString())

            if (alimentsNamesList!!.isNotEmpty() && alimentsCaloriesList!!.isNotEmpty()) {
                isAlimentFound = true
            }

            if (isAlimentFound) {
                tv_aliment_search_result.text =
                    alimentsNamesList!!.size.toString() + " aliments found"
                tv_aliment_search_result.visibility = View.VISIBLE

                buildAlimentsFound()

                val adapter = DataSearchAdapter(
                    parentFragment!!.activity!!.applicationContext,
                    alimentsSearched
                )
                recyclerViewSearchFound.adapter = adapter
                recyclerViewSearchFound.visibility = View.VISIBLE

            } else {
                tv_aliment_search_result.text =
                    "No results found in the database. Do you wish to add $alimentNameInputToStock as a new Aliment ?"
                tv_aliment_search_result.visibility = View.VISIBLE
                container_no_result_prompts.visibility = View.VISIBLE
            }
        }
    }

    private fun buildAlimentsFound() {
        alimentsSearched = ArrayList()
        for (i in 0 until alimentsNamesList!!.size) {
            alimentsSearched.add(
                Aliment(
                    alimentsNamesList!![i],
                    alimentsCaloriesList!![i],
                    100
                )
            )
        }
    }


    private fun setExitFab() {
        fab_exit_food_data_search.setOnClickListener {
            activity!!.onBackPressed()
        }
    }

    private fun clearSearchParams() {
        alimentNameInputToStock = ""
        alimentCaloriesInput = 0
        alimentWeight = 100
        isAlimentFound = false
        userAnswer = 0

        tv_aliment_search_result.visibility = View.GONE
        recyclerview_search_aliment_found.visibility = View.GONE
        container_add_aliment_search.visibility = View.GONE
        container_no_result_prompts.visibility = View.GONE
    }
}
