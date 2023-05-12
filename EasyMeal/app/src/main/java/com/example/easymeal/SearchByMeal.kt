package com.example.easymeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.room.Room
import com.example.easymeal.database.Meal
import com.example.easymeal.database.MealsDatabase
import com.example.easymeal.repository.DatabaseRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchByMeal : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_meal)


        searchEditText = findViewById(R.id.editTxtSearchNm)
        searchBtn = findViewById(R.id.imageButton3)


        searchBtn.setOnClickListener {
            getDataFromDB()
        }

    }


    fun getDataFromDB(){

        var searchResultMealsList = mutableListOf<Meal>()

        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        val dbRepo = DatabaseRepository()
        val searchName = dbRepo.getSearchInputName(searchEditText)

        runBlocking {
            launch {
                searchResultMealsList = mealDao.getSearchMeals("%$searchName%") as ArrayList<Meal>
            }
        }

        viewMeals(searchResultMealsList)

    }

    fun viewMeals(mealsArr: MutableList<Meal>){
        val tv: TextView = findViewById(R.id.textView12)

        runBlocking {
            launch {
                val mealsText = StringBuilder()

                for (i in 0 until mealsArr.size){
                    mealsText.append(mealsArr[i].strMeal.toString()).append("\n")
                }

                tv.text = mealsText.toString()
            }
        }
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