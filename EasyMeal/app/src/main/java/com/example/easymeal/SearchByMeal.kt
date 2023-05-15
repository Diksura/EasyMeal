package com.example.easymeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.easymeal.database.Meal
import com.example.easymeal.database.MealsDatabase
import com.example.easymeal.repository.MealOnClickPopUp
import com.example.easymeal.resultMealsView.ResultsActivityAdaptor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchByMeal : AppCompatActivity(), ResultsActivityAdaptor.MealItemListener {

    private lateinit var searchEditText: EditText
    private lateinit var searchBtn: ImageButton

    private var searchName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_meal)


        searchEditText = findViewById(R.id.editTxtSearchNm)
        searchBtn = findViewById(R.id.btnSearchByMeal)


        searchBtn.setOnClickListener {
            searchName = searchEditText.text.toString()
            getDataFromDB(searchName)
        }

    }

    private fun getDataFromDB(searchName: String){

        var searchResultMealsList = mutableListOf<Meal>()

        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                searchResultMealsList = mealDao.getSearchMeals("%$searchName%") as ArrayList<Meal>
            }
        }

        viewMeals(searchResultMealsList)

    }

    private fun viewMeals(mealsArr: MutableList<Meal>){

        val recyclerView: RecyclerView = findViewById(R.id.searchByMealRclView)

        val adapter = ResultsActivityAdaptor(this, mealsArr, this)
        recyclerView.adapter = adapter
    }

    override fun onMealItemClick(meal: Meal) {
        val callMealOnClickPopUp = MealOnClickPopUp()
        callMealOnClickPopUp.mealDetailsPopUp(this,meal)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("searchName", searchName)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        searchName = savedInstanceState.getString("searchName").toString()

        if (searchName != ""){
            getDataFromDB(searchName)
        }
    }
}