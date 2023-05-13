package com.example.easymeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.easymeal.dataAdding.beefBanhMiBowls
import com.example.easymeal.dataAdding.chickenMarengo
import com.example.easymeal.dataAdding.leblebiSoup
import com.example.easymeal.dataAdding.sweetAndSourPork
import com.example.easymeal.database.Meal
import com.example.easymeal.resultMealsView.ResultsActivityAdaptor

class MainActivity22 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main22)


        var mealsArr = mutableListOf<Meal>()

        mealsArr.add(sweetAndSourPork)
        mealsArr.add(chickenMarengo)
        mealsArr.add(beefBanhMiBowls)
        mealsArr.add(leblebiSoup)

        Log.i("testLen", mealsArr.size.toString())

        val recyclerView: RecyclerView = findViewById(R.id.rcy)

        val adapter = ResultsActivityAdaptor(this, mealsArr)
        recyclerView.adapter = adapter

    }
}