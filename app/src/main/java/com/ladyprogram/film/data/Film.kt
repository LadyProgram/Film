package com.ladyprogram.film.data

import com.google.gson.annotations.SerializedName


data class FilmResponse (
    val response: String,
    val results: List<Film>
)


data class Film (
    @SerializedName("i") val id: String,
    val Title: String,
    val Year: String,
    val Poster: Poster,
    val Plot: String,
    val Runtime: String,
    val Director: String,
    val Genre: String,
    val Country: String
){
}

class Poster (val url: String)