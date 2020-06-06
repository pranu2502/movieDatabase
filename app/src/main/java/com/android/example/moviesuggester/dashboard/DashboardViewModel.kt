package com.android.example.moviesuggester.dashboard

import android.R
import android.app.Activity
import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.moviesuggester.network.MovieApi
import com.android.example.moviesuggester.network.MovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


enum class MovieApiStatus { LOADING, ERROR, DONE }
enum class SearchStatus {SEARCHED,NOT,ERROR}
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

    private val _status = MutableLiveData<MovieApiStatus>()

    val status: LiveData<MovieApiStatus>
        get() = _status

    private val _searchStatus = MutableLiveData<SearchStatus>()

    val searchStatus:LiveData<SearchStatus>
        get() = _searchStatus


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )


    init
    {

            _searchStatus.value = SearchStatus.NOT
    }


   fun getText(movie:String){
       getMovies(movie)

    }

    private fun getMovies(movie:String) {
        coroutineScope.launch {


            var getDetailsDeferred = MovieApi.retrofitService.getMovies(movie,"1")

//            var results:ResultDetails? = new ResultDetails()


            try {
                _status.value = MovieApiStatus.LOADING
                var results = getDetailsDeferred.await()
                _searchStatus.value=SearchStatus.SEARCHED
                _status.value = MovieApiStatus.DONE
                _details.value = results.Search


//                for(i in 2..((results.totalResults).toInt())/10)
//                {
//                    getDetailsDeferred = MovieApi.retrofitService.getMovies(movie,"${i}")
//                    try{
//                        results.Search = results.Search + getDetailsDeferred.await().Search
//                        _details.value = results.Search
//                    } catch (e:Exception){
//
//                    }
//                }

            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
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

