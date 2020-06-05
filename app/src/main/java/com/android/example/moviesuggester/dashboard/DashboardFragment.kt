package com.android.example.moviesuggester.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.android.example.moviesuggester.R
import com.android.example.moviesuggester.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDashboardBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.dashboardViewModel = dashboardViewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            dashboardViewModel.displayPropertyDetails(it)
        })

        val edit = binding.root.findViewById<EditText>(R.id.editText)

//        dashboardViewModel.movieSearch.observe(this, Observer {
//            if(it != null){
//                dashboardViewModel.getText(edit.text.toString())
//            }
//        })
        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int,i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input

                    dashboardViewModel.getText(editable.toString())
            }
        })

//        var animFadein = AnimationUtils.loadAnimation(
//            context,
//            R.anim.slide_in_left)

        dashboardViewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    DashboardFragmentDirections.actionShowDetail(it)
                )
                dashboardViewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }



    }


