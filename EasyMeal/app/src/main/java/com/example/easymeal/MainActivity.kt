package com.example.easymeal

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.room.Room
import com.example.easymeal.dataAdding.beefBanhMiBowls
import com.example.easymeal.dataAdding.chickenMarengo
import com.example.easymeal.dataAdding.leblebiSoup
import com.example.easymeal.dataAdding.sweetAndSourPork
import com.example.easymeal.database.Meal
import com.example.easymeal.database.MealsDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

//    val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
//    val mealDao = db.mealDao()

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

    private fun searchByIngredient(){
        val openSearchByIngredient = Intent(this, SearchByIngredient::class.java)
        startActivity(openSearchByIngredient)
    }

    private fun searchByMeal(){
        val openSearchByMeal = Intent(this, SearchByMeal::class.java)
        startActivity(openSearchByMeal)
    }

    private fun addMealsToDB(){
        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "MyDatabase").allowMainThreadQueries().build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                mealDao.insertMeal(sweetAndSourPork)
                mealDao.insertMeal(chickenMarengo)
                mealDao.insertMeal(beefBanhMiBowls)
                mealDao.insertMeal(leblebiSoup)

                val meals: List<Meal> = mealDao.getAll()
                for (meal_ in meals){
                    println(meal_)
                }
            }
        }

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_add_meals_to_db)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        dialog.show()
    }




}