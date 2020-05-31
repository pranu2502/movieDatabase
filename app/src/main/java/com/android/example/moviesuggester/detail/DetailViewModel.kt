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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.moviesuggester.network.MovieDetails
import com.android.example.moviesuggester.network.MovieSpecificApi
import com.android.example.moviesuggester.network.MovieSpecifics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(movieDetails: MovieDetails, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<MovieDetails>()
    val selectedProperty: LiveData<MovieDetails>
        get() = _selectedProperty

    private val _specifics = MutableLiveData<MovieSpecifics>()
    val specifics:LiveData<MovieSpecifics>
        get() = _specifics

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    init {
        _selectedProperty.value = movieDetails
        getSpecifics()
    }

    private fun getSpecifics(){
        coroutineScope.launch {
            var getSpecificsDeferred = MovieSpecificApi.retrofitService.getSpecifics(selectedProperty.value?.imdbID.toString())
            try{
                var results = getSpecificsDeferred.await()
                _specifics.value = results
            } catch (e:Exception){

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
