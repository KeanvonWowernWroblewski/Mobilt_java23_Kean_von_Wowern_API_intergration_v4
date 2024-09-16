package com.example.apiintegrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity1 : AppCompatActivity() {

    private lateinit var buttonSearch: Button
    private lateinit var buttonRandomCocktail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)

        buttonSearch = findViewById(R.id.buttonSearch)
        buttonRandomCocktail = findViewById(R.id.buttonRandom)

        buttonSearch.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }


        buttonRandomCocktail.setOnClickListener {
            val intent = Intent(this, Random::class.java)
            startActivity(intent)
        }
    }
}
