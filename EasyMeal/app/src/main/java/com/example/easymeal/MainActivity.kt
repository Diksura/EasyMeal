package com.example.easymeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.room.Room
import com.example.easymeal.database.MealsDatabase
import com.example.easymeal.repository.AddMealsToDBRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {


    private lateinit var btnAddMealsDB : Button
    private lateinit var btnSearchByIngredient : Button
    private lateinit var btnSearchByMeal : Button
    private lateinit var btnSearchFromWeb: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Deleting existing data saved in database.
        deleteDB()

        btnAddMealsDB = findViewById(R.id.btnAddMeal)
        btnSearchByIngredient = findViewById(R.id.btnSearchIngredient)
        btnSearchByMeal = findViewById(R.id.btnSearchMeal)
        btnSearchFromWeb = findViewById(R.id.btnSearchFromWeb)

        btnAddMealsDB.setOnClickListener { addMealsToDB() }
        btnSearchByIngredient.setOnClickListener { searchByIngredient() }
        btnSearchByMeal.setOnClickListener { searchByMeal() }
        btnSearchFromWeb.setOnClickListener { searchMealFromWeb() }
    }

    private fun searchByIngredient(){
        val openSearchByIngredient = Intent(this, SearchByIngredient::class.java)
        startActivity(openSearchByIngredient)
    }

    private fun searchByMeal(){
        val openSearchByMeal = Intent(this, SearchByMeal::class.java)
        startActivity(openSearchByMeal)
    }

    private fun addMealsToDB(){
        val callAddMealsToDB = AddMealsToDBRepository()
        callAddMealsToDB.processAddMealsToDB(this)
    }

    private fun searchMealFromWeb(){
        val openSearchByWeb = Intent(this, SearchMealFromWeb::class.java)
        startActivity(openSearchByWeb)
    }

    private fun deleteDB(){
        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                mealDao.deleteAll()
                Log.i("checkStatDBDel", mealDao.getAll().toString())
            }
        }
    }





}