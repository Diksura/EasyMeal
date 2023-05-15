package com.example.easymeal.repository

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room
import com.example.easymeal.R
import com.example.easymeal.dataAdding.beefBanhMiBowls
import com.example.easymeal.dataAdding.chickenMarengo
import com.example.easymeal.dataAdding.leblebiSoup
import com.example.easymeal.dataAdding.sweetAndSourPork
import com.example.easymeal.database.Meal
import com.example.easymeal.database.MealsDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddMealsToDBRepository {

    fun processAddMealsToDB(context: Context){

        saveHardCodeFilesDB(context)

    }

    private fun saveHardCodeFilesDB(context: Context){
        val db = Room.databaseBuilder(context, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()


        runBlocking {
            launch {
                mealDao.insertMeal(sweetAndSourPork)
                mealDao.insertMeal(chickenMarengo)
                mealDao.insertMeal(beefBanhMiBowls)
                mealDao.insertMeal(leblebiSoup)

                // fot testing purposes
                //From Here
                val meals: List<Meal> = mealDao.getAll()
                for (meal_ in meals) {
                    println(meal_)
                    Log.i("checkStatDB", meal_.toString())

                }

                Log.i("checkStatDBall", mealDao.getAll().toString())
                // To Here

                popupDialog(context)
            }
        }

    }

    private fun popupDialog(context: Context){

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_add_meals_to_db)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val imgViewAddDbDone: ImageView = dialog.findViewById(R.id.imgViewAddDbDone)
        val successMsg: TextView = dialog.findViewById(R.id.successMsg)

        imgViewAddDbDone.setImageResource(R.drawable.done)
        successMsg.text = "Successfully Added To Database"

        dialog.show()
    }
}