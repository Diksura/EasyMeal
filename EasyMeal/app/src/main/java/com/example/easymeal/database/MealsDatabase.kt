package com.example.easymeal.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Meal::class], version = 1)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealDao(): MealsDao
}