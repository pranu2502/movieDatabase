package com.android.example.moviesuggester.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ResultDetails(
    val Search:List<MovieDetails>,
    val totalResults:String,
    val Response:String
)


@Parcelize
data class MovieDetails(
    val Title:String,
    val Year:String,
    val imdbID:String,
    val Type:String,
    val Poster:String):Parcelable

@Parcelize
data class MovieSpecifics(
    val Title: String,
    val Year: String,
    val  Rated:String,
    val Runtime:String,
    val Released:String,
    val Genre:String,
    val Director:String,
    val Actors:String,
    val Plot: String,
    val Language:String,
    val Country:String,
    val Awards: String,
    val Poster : String,
    val Ratings:List<RatingSources>,
    val Type: String,
    val BoxOffice:String,
    val Production:String,
    var Genre_helper:List<String>,
    val imdbRating:String
):Parcelable


@Parcelize
data class RatingSources(
    val Source:String,
    val Value:String
):Parcelable
