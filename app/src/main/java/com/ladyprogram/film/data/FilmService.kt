package com.ladyprogram.film.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmService {

    @GET("#")
    suspend fun findFilmByName(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = "1e6219c6",
        ): FilmResponse

    @GET("#")
    suspend fun findFilmById(@Path("i") id: String): Film

    companion object {
        fun getRetrofit(): FilmService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(FilmService::class.java)
        }
    }
}