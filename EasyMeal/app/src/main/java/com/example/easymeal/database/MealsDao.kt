package com.example.easymeal.database

import androidx.room.*

@Dao
interface MealsDao {

    @Query("SELECT * FROM mealsTable")
    suspend fun getAll(): List<Meal>

    @Query("SELECT * FROM mealsTable WHERE lower(strIngredient1) LIKE '%' || :mealName || '%' " +
            "OR lower(strIngredient2) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient3) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient4) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient5) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient6) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient7) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient8) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient9) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient10) LIKE '%' || lower(:mealName) || '%' "+
            "OR lower(strIngredient11) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient12) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient13) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient14) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient15) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient16) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient17) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient18) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient19) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strIngredient20) LIKE '%' || lower(:mealName) || '%' " +
            "OR lower(strMeal) LIKE '%' || lower(:mealName) || '%' ")
    suspend fun getSearchMeals(mealName: String): List<Meal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Insert
    suspend fun insertSetOfMeals(mealsList: List<Meal>)

    @Query("DELETE from mealsTable")
    suspend fun deleteAll()

}