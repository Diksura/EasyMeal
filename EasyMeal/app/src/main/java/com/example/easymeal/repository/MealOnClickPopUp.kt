package com.example.easymeal.repository

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.widget.TextView
import com.example.easymeal.R
import com.example.easymeal.database.Meal
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MealOnClickPopUp {

    fun mealDetailsPopUp(context: Context, meal: Meal){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.meal_item_sel_popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)


        val txtMealName: TextView = dialog.findViewById(R.id.popUpmeal_name)
        val txtMealCategory: TextView = dialog.findViewById(R.id.popUpmeal_category)
        val txtMealArea: TextView = dialog.findViewById(R.id.popUpmeal_area)
        val txtMealInstruction: TextView = dialog.findViewById(R.id.popUpmeal_instruction)
        val txtMealYoutube: TextView = dialog.findViewById(R.id.popUpmeal_youtube)
        val txtMealIngredient: TextView = dialog.findViewById(R.id.popUpmeal_ingredient)
        val txtMealMeasure: TextView = dialog.findViewById(R.id.popUpmeal_measure)


        txtMealName.text = meal.strMeal
        txtMealCategory.text = meal.strCategory
        txtMealArea.text = meal.strArea
        txtMealInstruction.text = meal.strInstructions
        txtMealYoutube.text = meal.strYoutube

        runBlocking {
            launch {
                val ingredientText = StringBuilder()
                val measureText = StringBuilder()

                for (i in 0 until setIngredientsToList(meal).size){
                    if (setIngredientsToList(meal)[i] != "") {
                        ingredientText.append(setIngredientsToList(meal)[i].toString()).append("\n")
                        measureText.append(setMeasuresToList(meal)[i].toString()).append("\n")
                    }
                }
                txtMealIngredient.text = ingredientText.toString()
                txtMealMeasure.text = measureText.toString()

            }
        }


        dialog.show()
    }

    private fun setIngredientsToList(meal: Meal): MutableList<String?> {

        return mutableListOf(
            meal.strIngredient1,
            meal.strIngredient2,
            meal.strIngredient3,
            meal.strIngredient4,
            meal.strIngredient5,
            meal.strIngredient6,
            meal.strIngredient7,
            meal.strIngredient8,
            meal.strIngredient9,
            meal.strIngredient10,
            meal.strIngredient11,
            meal.strIngredient12,
            meal.strIngredient13,
            meal.strIngredient14,
            meal.strIngredient15,
            meal.strIngredient16,
            meal.strIngredient17,
            meal.strIngredient18,
            meal.strIngredient19,
            meal.strIngredient20
        )
    }

    private fun setMeasuresToList(meal: Meal): MutableList<String?> {

        return mutableListOf(
            meal.strMeasure1,
            meal.strMeasure2,
            meal.strMeasure3,
            meal.strMeasure4,
            meal.strMeasure5,
            meal.strMeasure6,
            meal.strMeasure7,
            meal.strMeasure8,
            meal.strMeasure9,
            meal.strMeasure10,
            meal.strMeasure11,
            meal.strMeasure12,
            meal.strMeasure13,
            meal.strMeasure14,
            meal.strMeasure15,
            meal.strMeasure16,
            meal.strMeasure17,
            meal.strMeasure18,
            meal.strMeasure19,
            meal.strMeasure20
        )
    }
}