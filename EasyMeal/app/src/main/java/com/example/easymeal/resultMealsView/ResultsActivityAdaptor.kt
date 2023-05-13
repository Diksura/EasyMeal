package com.example.easymeal.resultMealsView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easymeal.R
import com.example.easymeal.database.Meal

class ResultsActivityAdaptor(val context: Context, val mealsList: MutableList<Meal>): RecyclerView.Adapter<ResultsActivityAdaptor.ViewHolder>()
{

    override fun getItemCount() = mealsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.result_meal_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = mealsList[position]

        with(holder){
            resultMealName?.let {
                it.text = meal.strMeal
                it.contentDescription = meal.strMeal
            }

            resultMealCategory?.let {
                it.text = meal.strCategory
                it.contentDescription = meal.strCategory
            }

        }
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val resltMealThumbnail = itemView.findViewById<ImageView>(R.id.resultMealThumbnail)
        val resultMealName = itemView.findViewById<TextView>(R.id.resultMealName)
        val resultMealCategory = itemView.findViewById<TextView>(R.id.resultMealCategory)

    }

}