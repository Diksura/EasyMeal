package com.example.easymeal.database

import androidx.room.*

@Dao
interface MealsDao {

    @Query("SELECT * FROM mealsTable")
    suspend fun getAll(): List<Meal>

    @Query("SELECT * FROM mealsTable WHERE strMeal LIKE 'Chicken%'")
    suspend fun getSearchMeals(): List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Insert
    suspend fun insertSetOfMeals(mealsList: List<Meal>)

    @Query("DELETE from mealsTable")
    suspend fun deleteAll()

}