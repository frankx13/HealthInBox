package com.studio.neopanda.healthinbox.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.studio.neopanda.healthinbox.R
import com.studio.neopanda.healthinbox.database.Aliment
import com.studio.neopanda.healthinbox.database.AlimentViewModel
import kotlinx.android.synthetic.main.fragment_data_search.*

class DataSearchFragment : Fragment() {

    private var alimentNameInputToStock: String = ""
    private var alimentInputToRead: String = ""
    private var alimentCaloriesInput = 0
    private var alimentWeight = 100
    private var alimentViewModel: AlimentViewModel? = null
    private var isAlimentFound: Boolean = false
    private var userAnswer = 0
    private lateinit var alimentsSearched: List<Aliment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

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
                    "The $alimentInputToRead has been added to your database!",
                    Toast.LENGTH_LONG
                ).show()
                container_add_aliment_search.visibility = View.GONE
            }
        }
    }

    private fun manageInputUnknownAliment() {
        btn_add_unknown_aliment.setOnClickListener {
            container_no_result_prompts.visibility = View.GONE
            tv_aliment_search_result.visibility = View.GONE
            userAnswer = 2
            inputUnknownAliment(userAnswer)
        }

        btn_ignore_unknown_aliment.setOnClickListener {
            container_no_result_prompts.visibility = View.GONE
            tv_aliment_search_result.visibility = View.GONE
            userAnswer = 1
            inputUnknownAliment(userAnswer)
        }
    }

    private fun validateUserInput() {
        btn_validate_aliment_search_input.setOnClickListener {
            alimentNameInputToStock =
                "%" + et_search_aliment_input.editableText.toString().trim() + "%"
            alimentInputToRead = et_search_aliment_input.editableText.toString().trim()
//            alimentsSearched = alimentViewModel!!.searchAliments(alimentNameInputToStock)
//            //TODO add a callback inside AsyncTask of repo to get the list
//            isAlimentFound = alimentsSearched.isNotEmpty()


            if (isAlimentFound) {
                tv_aliment_search_result.text = resources.getString(R.string.result_found_text)
                tv_aliment_search_result.visibility = View.VISIBLE
                //Todo Implement the RecyclerView logic
            } else {
                tv_aliment_search_result.text =
                    "No results found in the database. Do you wish to add $alimentInputToRead as a new Aliment ?"
                tv_aliment_search_result.visibility = View.VISIBLE
                container_no_result_prompts.visibility = View.VISIBLE
            }
        }
    }

    private fun inputUnknownAliment(userInputAnswer: Int) {
        if (userInputAnswer == 2) {
            container_add_aliment_search.visibility = View.VISIBLE
        } else {
            userAnswer = 0
        }
    }

    private fun setExitFab() {
        fab_exit_food_data_search.setOnClickListener {
            activity!!.onBackPressed()
        }
    }
}
