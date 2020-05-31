package com.android.example.moviesuggester.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.moviesuggester.network.MovieApi
import com.android.example.moviesuggester.network.MovieDetails
import com.android.example.moviesuggester.network.ResultDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//enum class MarsApiStatus { LOADING, ERROR, DONE }

class DashboardViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
//    private val _status = MutableLiveData<MarsApiStatus>()
//
//    // The external immutable LiveData for the request status
//    val status: LiveData<MarsApiStatus>
//        get() = _status

    private val _details = MutableLiveData<List<MovieDetails>>()

    val details: LiveData<List<MovieDetails>>
        get() = _details

    private val _navigateToSelectedProperty = MutableLiveData<MovieDetails>()
    val navigateToSelectedProperty: LiveData<MovieDetails>
        get() = _navigateToSelectedProperty

    private val _movieSearch = MutableLiveData<String>()

    val movieSearch:LiveData<String>
        get() = _movieSearch

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        println("ojay")
        getMovies()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMovies() {
        coroutineScope.launch {

            var getDetailsDeferred = MovieApi.retrofitService.getMovies("batman","1")

            try {
//                _status.value = MarsApiStatus.LOADING
                var results = getDetailsDeferred.await()
//                _status.value = MarsApiStatus.DONE
                _details.value = results.Search

            } catch (e: Exception) {
//                _status.value = MarsApiStatus.ERROR
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