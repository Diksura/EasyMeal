package com.example.easymeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.room.Room
import com.example.easymeal.database.MealsDatabase
import com.example.easymeal.repository.DatabaseRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchByMeal : AppCompatActivity() {

    lateinit var searchEditText: EditText
    lateinit var searchBtn: ImageButton

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
        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        val DBRepo = DatabaseRepository()
        val searchName = DBRepo.getSearchInputName(searchEditText)

        val tv: TextView = findViewById(R.id.textView12)
        tv.setText(searchName)


//
//        runBlocking {
//            launch {
//                //add code
//            }
//        }

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