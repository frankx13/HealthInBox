package com.studio.neopanda.healthinbox.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.studio.neopanda.healthinbox.R
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_food_data.*

class FoodDataFragment : Fragment() {

//    private var viewFoodData: View? = null
    private var adapterFoodData: AdapterFoodData? = null
    private var dataAllFragment : Fragment = DataAllFragment()
    private var dataCategoriesFragment : Fragment = DataCategoriesFragment()
    private var dataSearchFragment : Fragment = DataSearchFragment()

    private fun setupViewPager(viewPager: ViewPager) {
        //We pass ChildFragmentManager instead of FragmentManager.
        adapterFoodData = AdapterFoodData(childFragmentManager)
        adapterFoodData!!.addFragment(dataAllFragment, "All")
        adapterFoodData!!.addFragment(dataCategoriesFragment, "Categories")
        adapterFoodData!!.addFragment(dataSearchFragment, "Search")
        viewPager.adapter = adapterFoodData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_food_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(viewpager_food_data as ViewPager)
        tabs_food_data.setupWithViewPager(viewpager_food_data)
    }

    internal class AdapterFoodData(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private var mFragments = ArrayList<Fragment>()
        private var mFragmentTitles = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String) {
            Log.e("FRAGS", "$fragment   $title")
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }
    }
}


//    private fun workingAlimentGeneratorClassVars(){
//        private var alimentViewModel: AlimentViewModel? = null
//        private val ADD_ALIMENT_REQUEST = 1
//        private val EDIT_ALIMENT_REQUEST = 2
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == ADD_ALIMENT_REQUEST && resultCode == Activity.RESULT_OK) {
//            val name = data!!.getStringExtra(AddEditAlimentActivity.EXTRA_NAME)
//            val calories = data.getIntExtra(AddEditAlimentActivity.EXTRA_CALORIES, 1)
//            val weight = data.getIntExtra(AddEditAlimentActivity.EXTRA_WEIGHT, 1)
//
//            val aliment = Aliment(name!!, calories, weight)
//            alimentViewModel!!.insert(aliment)
//
//            Toast.makeText(activity, "Aliment saved", Toast.LENGTH_SHORT).show()
//        } else if (requestCode == EDIT_ALIMENT_REQUEST && resultCode == Activity.RESULT_OK) {
//            val id = data!!.getIntExtra(AddEditAlimentActivity.EXTRA_ID, -1)
//            if (id == -1) {
//                Toast.makeText(activity, "Aliment cannot be saved", Toast.LENGTH_SHORT).show()
//                return
//            }
//
//            val name = data.getStringExtra(AddEditAlimentActivity.EXTRA_NAME)
//            val calories = data.getIntExtra(AddEditAlimentActivity.EXTRA_CALORIES, 1)
//            val weight = data.getIntExtra(AddEditAlimentActivity.EXTRA_WEIGHT, 1)
//
//            val aliment = Aliment(name!!, calories, weight)
//            aliment.id = id
//            alimentViewModel!!.update(aliment)
//
//            Toast.makeText(activity, "Aliment updated", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(activity, "Aliment not updated", Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun workingAlimentGeneratorInsideOnCreateView(){
    //        setExitFab()
//
//        val recyclerView = recyclerview
//        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
//        recyclerView.setHasFixedSize(true)
//
//        val adapter = AlimentAdapter()
//        recyclerView.adapter = adapter
//
//        //OBSERVE ALIMENTS
//        alimentViewModel = ViewModelProviders.of(this).get(AlimentViewModel::class.java)
//        alimentViewModel!!.getAllAliments().observe(
//            this,
//            Observer<List<Aliment>>(fun(aliments: List<Aliment>) {
//                adapter.submitList(null)
//                adapter.notifyDataSetChanged()
//                adapter.submitList(aliments) //even though aliments exist, they are not displayed unless we add something (= until the observer notify a change)
//                adapter.notifyDataSetChanged()
//                recyclerView.smoothScrollToPosition(adapter.itemCount)
//
//                Toast.makeText(activity!!.applicationContext, "OBSERVING", Toast.LENGTH_SHORT)
//                    .show()
//            })
//        )
//
//        //EDIT ONE ALIMENT
//        adapter.setOnItemClickListener(object : AlimentAdapter.OnItemClickListener {
//            override fun onItemClick(aliment: Aliment) {
//                val intent = Intent(activity, AddEditAlimentActivity::class.java)
//
//                intent.putExtra(AddEditAlimentActivity.EXTRA_ID, aliment.id)
//                intent.putExtra(AddEditAlimentActivity.EXTRA_NAME, aliment.name)
//                intent.putExtra(AddEditAlimentActivity.EXTRA_CALORIES, aliment.calories)
//                intent.putExtra(AddEditAlimentActivity.EXTRA_WEIGHT, aliment.weight)
//
//                startActivityForResult(intent, EDIT_ALIMENT_REQUEST)
//            }
//        })
//
//        //ADD ONE ALIMENT
//        val fabAddAliment = fab_add_aliment
//        fabAddAliment.setOnClickListener {
//            val intent = Intent(activity, AddEditAlimentActivity::class.java)
//            startActivityForResult(intent, ADD_ALIMENT_REQUEST)
//        }
//
//        //REMOVE ALL ALIMENTS
//        val fabDeleteAllAliments = fab_delete_all_aliments
//        fabDeleteAllAliments.setOnClickListener {
//            alimentViewModel!!.deleteAllAliments()
//            Toast.makeText(activity, "Aliments nuked", Toast.LENGTH_SHORT).show()
//        }
//
//        //REMOVE ONE ALIMENT
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                alimentViewModel!!.delete(adapter.getAlimentAt(viewHolder.adapterPosition))
//                Toast.makeText(activity, "Aliment deleted", Toast.LENGTH_SHORT).show()
//            }
//        }).attachToRecyclerView(recyclerView)
//    }

//    private fun setExitFab() {
//        fab_exit_food_data.setOnClickListener {
//            activity!!.onBackPressed()
//        }
//    }
