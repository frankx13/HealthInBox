package com.studio.neopanda.healthinbox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.studio.neopanda.healthinbox.R
import kotlinx.android.synthetic.main.fragment_food_data.*

class FoodDataFragment : Fragment() {

    //    private var viewFoodData: View? = null
    private var adapterFoodData: AdapterFoodData? = null
    private var dataAllFragment: Fragment = DataAllFragment()
    private var dataCategoriesFragment: Fragment = DataCategoriesFragment()
    private var dataSearchFragment: Fragment = DataSearchFragment()

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
