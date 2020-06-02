package com.android.example.moviesuggester.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "http://www.omdbapi.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun <T> merge(first:List<T>,second:List<T>):List<T>{
    return first.plus(second)
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

///?apikey=6d48273c&
interface MovieApiService {
    @GET("/?apikey=6d48273c")
    fun getMovies(
        @Query("s") movie: String?,
        @Query("page") page: String

    ): Deferred<ResultDetails>
}

interface MovieSpecificApiService {
    @GET("/?apikey=6d48273c")
    fun getSpecifics(
        @Query("i")imdbID:String,
        @Query("plot")plot:String
    ):Deferred<MovieSpecifics>
}

object MovieApi {
    val retrofitService : MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java) }
}

object MovieSpecificApi{
    val retrofitService:MovieSpecificApiService by lazy{
        retrofit.create(MovieSpecificApiService::class.java) }
}
