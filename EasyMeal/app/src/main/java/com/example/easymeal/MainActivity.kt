package com.example.easymeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnAddMealsDB : Button
    private lateinit var btnSearchByIngredient : Button
    private lateinit var btnSearchByMeal : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAddMealsDB = findViewById(R.id.btnAddMeal)
        btnSearchByIngredient = findViewById(R.id.btnSearchIngredient)
        btnSearchByMeal = findViewById(R.id.btnSearchMeal)

        btnAddMealsDB.setOnClickListener { addMealsToDB() }
        btnSearchByIngredient.setOnClickListener { searchByIngredient() }
        btnSearchByMeal.setOnClickListener { searchByMeal() }
    }

    private fun addMealsToDB(){
        val openAddMealsToDB = Intent(this, AddMealsToDB::class.java)
        startActivity(openAddMealsToDB)
    }

    private fun searchByIngredient(){
        val openSearchByIngredient = Intent(this, SearchByIngredient::class.java)
        startActivity(openSearchByIngredient)
    }

    private fun searchByMeal(){
        val openSearchByMeal = Intent(this, SearchByMeal::class.java)
        startActivity(openSearchByMeal)
    }


}