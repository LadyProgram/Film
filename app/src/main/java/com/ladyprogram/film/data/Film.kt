package com.ladyprogram.film.data

import com.google.gson.annotations.SerializedName


data class FilmResponse (
    val response: String,
    @SerializedName("Search") val results: List<Film>
)


data class Film (
    @SerializedName("imdbID") val id: String,
    val Title: String,
    val Year: String,
    val Poster: String,
    val Plot: String,
    val Runtime: String,
    val Director: String,
    val Genre: String,
    val Country: String
){
}

class Poster (val url: String)