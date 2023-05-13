package com.example.easymeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.room.Room
import com.example.easymeal.database.MealsDatabase
import com.example.easymeal.repository.AddMealsToDBRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {


    private lateinit var btnAddMealsDB : Button
    private lateinit var btnSearchByIngredient : Button
    private lateinit var btnSearchByMeal : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Deleting existing data saved in database.
        deleteDB()

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
        val openSearchByMeal = Intent(this, MainActivity22::class.java)
        startActivity(openSearchByMeal)
    }

    private fun addMealsToDB(){
        val callAddMealsToDB = AddMealsToDBRepository()
        callAddMealsToDB.processAddMealsToDB(this)


        /*
        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
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

                t1 =  meals[1].strMeal.toString()
                t2 = meals[1].strCategory.toString()
                t3 =  meals[1].strArea.toString()
                t4 =  meals[1].strYoutube.toString()
                t5 =  meals[1].strIngredient18.toString()
                t6 =  meals[1].strIngredient1.toString()
                t7 =  meals[1].strMeasure18.toString()
                t8 =  meals[1].strMeasure1.toString()
                t9 =  meals[1].strImageSource.toString()



                Log.i("checkStatDB", mealDao.getAll().toString())
                // To Here
            }
        }

        val dialog = Dialog(this)
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

         */
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