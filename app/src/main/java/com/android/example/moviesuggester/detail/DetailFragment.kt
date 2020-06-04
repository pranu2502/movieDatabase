/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.android.example.moviesuggester.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.example.moviesuggester.R
import com.android.example.moviesuggester.databinding.FragmentDetailsBinding



class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_details,container,false)

        val application = requireNotNull(activity).application

        val rating = binding.root.findViewById<RatingBar>(R.id.rating_stars)


        val movieDetails = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty

        val viewModelFactory = DetailViewModelFactory(movieDetails, application)

        val detailViewModel= ViewModelProviders.of( this, viewModelFactory ).get(DetailViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.detailViewModel = detailViewModel

        return binding.root
    }
}