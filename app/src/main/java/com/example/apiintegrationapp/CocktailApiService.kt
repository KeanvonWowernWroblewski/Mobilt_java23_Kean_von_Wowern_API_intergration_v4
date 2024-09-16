package com.example.apiintegrationapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("search.php")
    fun searchCocktailByName(@Query("s") query: String): Call<CocktailResponse>
}
