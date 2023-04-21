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

//        deleteDB()

    }


    fun getDataFromDB(){

        var searchResultMealsList = arrayListOf<Meal>()

        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        val dbRepo = DatabaseRepository()
        val searchName = dbRepo.getSearchInputName(searchEditText)

        val sampleTxtView: TextView = findViewById(R.id.textView12)
//        sampleTxtView.text = searchName



        runBlocking {
            launch {
                searchResultMealsList = mealDao.getSearchMeals(searchName) as ArrayList<Meal>

                for (i in 0 until searchResultMealsList.size){
                    val otPt = "Meal :" + i + " ," + searchResultMealsList[i].strMeal + " ," + searchResultMealsList[i].strArea
                    sampleTxtView.text = otPt
                }

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