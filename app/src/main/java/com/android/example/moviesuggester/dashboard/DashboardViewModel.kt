package com.android.example.moviesuggester.dashboard

import android.R
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.moviesuggester.network.MovieApi
import com.android.example.moviesuggester.network.MovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch




class DashboardViewModel : ViewModel() {


    private val _details = MutableLiveData<List<MovieDetails>>()

    val details: LiveData<List<MovieDetails>>
        get() = _details

    private val _navigateToSelectedProperty = MutableLiveData<MovieDetails>()
    val navigateToSelectedProperty: LiveData<MovieDetails>
        get() = _navigateToSelectedProperty

    private var _movieSearch = MutableLiveData<String>()

    val movieSearch:LiveData<String>
        get() = _movieSearch


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )


    init
    {
        getMovies("hello")
    }


   fun getText(movie:String){
       getMovies(movie)

    }

    private fun getMovies(movie:String) {
        coroutineScope.launch {

            var getDetailsDeferred = MovieApi.retrofitService.getMovies(movie,"1")

            try {

                var results = getDetailsDeferred.await()

                _details.value = results.Search

            } catch (e: Exception) {

                _details.value = ArrayList()

            }

        }
    }

    fun displayPropertyDetails(movieDetails: MovieDetails) {
        _navigateToSelectedProperty.value = movieDetails
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}