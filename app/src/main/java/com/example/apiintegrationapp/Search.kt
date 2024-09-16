package com.example.apiintegrationapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Search : AppCompatActivity() {

    private lateinit var cocktailImageView: ImageView
    private lateinit var cocktailNameTextView: TextView
    private lateinit var cocktailIngredientsTextView: TextView
    private lateinit var cocktailInstructionsTextView: TextView
    private lateinit var buttonSearch: Button
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        cocktailImageView = findViewById(R.id.cocktailImageView)
        cocktailNameTextView = findViewById(R.id.cocktailNameTextView)
        cocktailIngredientsTextView = findViewById(R.id.cocktailIngredientsTextView)
        cocktailInstructionsTextView = findViewById(R.id.cocktailInstructionsTextView)
        buttonSearch = findViewById(R.id.btnSearchCocktail)
        searchEditText = findViewById(R.id.editTextSearch)

        buttonSearch.setOnClickListener {
            val searchQuery = searchEditText.text.toString()
            if (searchQuery.isNotEmpty()) {
                fetchCocktailSearchResults(searchQuery)
            } else {
                Toast.makeText(this, "Please enter a cocktail name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCocktailSearchResults(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CocktailApiService::class.java)
        val call = api.searchCocktailByName(query)

        call.enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                val cocktail = response.body()?.drinks?.get(0)
                if (cocktail != null) {
                    displayCocktailData(cocktail)
                } else {
                    cocktailNameTextView.text = "No cocktail found"
                }
            }

            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                cocktailNameTextView.text = "Error"
            }
        })
    }

    private fun displayCocktailData(cocktail: Cocktail?) {
        if (cocktail != null) {
            cocktailNameTextView.text = cocktail.strDrink
            Glide.with(this)
                .load(cocktail.strDrinkThumb)
                .into(cocktailImageView)

            val ingredients = StringBuilder()
            appendIngredient(ingredients, cocktail.strMeasure1, cocktail.strIngredient1)
            appendIngredient(ingredients, cocktail.strMeasure2, cocktail.strIngredient2)
            appendIngredient(ingredients, cocktail.strMeasure3, cocktail.strIngredient3)
            appendIngredient(ingredients, cocktail.strMeasure4, cocktail.strIngredient4)
            appendIngredient(ingredients, cocktail.strMeasure4, cocktail.strIngredient5)
            appendIngredient(ingredients, cocktail.strMeasure4, cocktail.strIngredient6)
            appendIngredient(ingredients, cocktail.strMeasure4, cocktail.strIngredient7)

            cocktailIngredientsTextView.text = ingredients.toString()


            cocktailInstructionsTextView.text = cocktail.strInstructions
        }
    }

    private fun appendIngredient(ingredients: StringBuilder, measurement: String?, ingredient: String?) {
        if (!ingredient.isNullOrEmpty()) {
            ingredients.append("- ${measurement ?: ""} $ingredient\n")
        }
    }
}
