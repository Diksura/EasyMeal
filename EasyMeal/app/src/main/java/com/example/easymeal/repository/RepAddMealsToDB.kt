package com.example.easymeal.repository

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
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

class RepAddMealsToDB {

    fun processAddMealsToDB(context: Context){

        saveHardCodeFilesDB(context)
        popupDialog(context)

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
            }
        }

    }

    private fun popupDialog(context: Context){
        val db = Room.databaseBuilder(context, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        var t1 = ""
        var t2 = ""
        var t3 = ""
        var t4 = ""
        var t5 = ""
        var t6 = ""
        var t7 = ""
        var t8 = ""
        var t9 = ""

        runBlocking {
            launch {

                val meals: List<Meal> = mealDao.getAll()

                t1 = meals[1].strMeal.toString()
                t2 = meals[1].strCategory.toString()
                t3 = meals[1].strArea.toString()
                t4 = meals[1].strYoutube.toString()
                t5 = meals[1].strIngredient18.toString()
                t6 = meals[1].strIngredient1.toString()
                t7 = meals[1].strMeasure18.toString()
                t8 = meals[1].strMeasure1.toString()
                t9 = meals[1].strImageSource.toString()
            }
        }

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_add_meals_to_db)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val txt1: TextView = dialog.findViewById(R.id.textView1)
        val txt2: TextView = dialog.findViewById(R.id.textView2)
        val txt3: TextView = dialog.findViewById(R.id.textView3)
        val txt4: TextView = dialog.findViewById(R.id.textView4)
        val txt5: TextView = dialog.findViewById(R.id.textView5)
        val txt6: TextView = dialog.findViewById(R.id.textView6)
        val txt7: TextView = dialog.findViewById(R.id.textView7)
        val txt8: TextView = dialog.findViewById(R.id.textView8)
        val txt9: TextView = dialog.findViewById(R.id.textView9)

        txt1.setText(t1)
        txt2.setText(t2)
        txt3.setText(t3)
        txt4.setText(t4)
        txt5.setText(t5)
        txt6.setText(t6)
        txt7.setText(t7)
        txt8.setText(t8)
        txt9.setText(t9)



        dialog.show()
    }
}