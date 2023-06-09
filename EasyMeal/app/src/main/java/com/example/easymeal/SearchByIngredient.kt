package com.example.easymeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.easymeal.dataAdding.MealsList
import com.example.easymeal.database.Meal
import com.example.easymeal.database.MealsDatabase
import com.example.easymeal.repository.MealOnClickPopUp
import com.example.easymeal.repository.UtilityRepository
import com.example.easymeal.resultMealsView.ResultsActivityAdaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchByIngredient : AppCompatActivity(), ResultsActivityAdaptor.MealItemListener {

    private val getMealsList = MealsList()
    private var mealsArr = getMealsList.mealsArr

    private lateinit var btnSearch: Button
    private lateinit var btnSaveMeals: Button
    private lateinit var edTxtSearchBar: EditText
    private lateinit var recyclerView: RecyclerView

    private val utilityRepo = UtilityRepository(this)
    private var userInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_ingredient)

        btnSearch = findViewById(R.id.btnRetrieveMeals)
        btnSaveMeals = findViewById(R.id.btnSaveMeals)
        edTxtSearchBar = findViewById(R.id.inputSearchByIngredient)
        recyclerView = findViewById(R.id.searchByIngRclView)


        btnSaveMeals.isVisible = false

        // checking network availability and gives an error if network isn't available.
        utilityRepo.networkCheckAndGiveError()

        btnSearch.setOnClickListener {

            // again checking network status before searching
            utilityRepo.networkCheckAndGiveError()

            mealsArr.clear() // clear array if past search still exists.
            getSearchName(edTxtSearchBar)
            viewMealsInRecycleView() // calling for recycler view

            btnSaveMeals.isVisible = true
        }

        btnSaveMeals.setOnClickListener {
            addAllMealsToDB() // adding searched meals to database
        }
    }

    /**
     * In here to search by ingredient first search by ingredient using
     * https://www.themealdb.com/api/json/v1/1/filter.php?i= and it's giving a list of meals names,
     * but not full details, then putting all retreived meals to jasonArray and again using each
     * meal's id again search by id using https://www.themealdb.com/api/json/v1/1/lookup.php?i=,
     * by that stores full meal details in mealsArr.
     * */
    private fun getSearchName(inputText: EditText) {
        userInput = inputText.text.toString()
        val urlIngredientString = "https://www.themealdb.com/api/json/v1/1/filter.php?i=$userInput"

        readWebByIngredient(urlIngredientString)
    }

    private fun readWebByIngredient(urlIngredientString: String) {
        val ingredientStringBuilder = StringBuilder()

        val urlIngredient = URL(urlIngredientString)
        val connectURL: HttpURLConnection = urlIngredient.openConnection() as HttpURLConnection

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val ingredientBufReader =
                        BufferedReader(InputStreamReader(connectURL.inputStream))
                    var ingredientsLine: String? = ingredientBufReader.readLine()

                    while (ingredientsLine != null) {
                        ingredientStringBuilder.append(ingredientsLine + "\n")
                        ingredientsLine = ingredientBufReader.readLine()
                    }

                    parseIngredientJSON(ingredientStringBuilder)
                }
            }
        }

    }

    private fun parseIngredientJSON(stb: java.lang.StringBuilder) {
        val jsonIngredient = JSONObject(stb.toString())
        val mealsIngredient = java.lang.StringBuilder()

        try {
            val ingredientJsonArray: JSONArray = jsonIngredient.getJSONArray("meals")

            mealsIngredient.append(ingredientJsonArray)

            var eachIngredient = ""
            for (ing in 0 until ingredientJsonArray.length()) {
                val ingredient: JSONObject = ingredientJsonArray[ing] as JSONObject

                eachIngredient = ingredient["idMeal"] as String
                readFromWeb(eachIngredient)
            }

        } catch (e: JSONException) {

        }
    }

    private fun readFromWeb(eachIngredient: String) {
        val stringBuilder = StringBuilder()

        val urlString = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$eachIngredient"
        val url = URL(urlString)
        val connect: HttpURLConnection = url.openConnection() as HttpURLConnection

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val bufReader = BufferedReader(InputStreamReader(connect.inputStream))
                    var line: String? = bufReader.readLine()

                    while (line != null) {
                        stringBuilder.append(line + "\n")
                        line = bufReader.readLine()
                    }
                    // adding parse method
                    parseJSON(stringBuilder)
                }
            }
        }

    }

    private fun parseJSON(stb: java.lang.StringBuilder) {

        val json = JSONObject(stb.toString())
        val allMeals = java.lang.StringBuilder()

        val jsonArray: JSONArray = json.getJSONArray("meals")

        allMeals.append(jsonArray)
        Log.i("allBooks", allMeals.toString())


        //adding single data to Meal class
        for (i in 0 until jsonArray.length()) {
            val meal: JSONObject = jsonArray[i] as JSONObject

            val singleMeal = Meal(
                idMeal = meal["idMeal"] as? Int,
                strMeal = meal["strMeal"] as? String,
                strDrinkAlternate = meal["strDrinkAlternate"] as? String,
                strCategory = meal["strCategory"] as? String,
                strArea = meal["strArea"] as? String,
                strInstructions = meal["strInstructions"] as? String,
                strMealThumb = meal["strMealThumb"] as? String,
                strTags = meal["strTags"] as? String,
                strYoutube = meal["strYoutube"] as? String,
                strIngredient1 = meal["strIngredient1"] as? String,
                strIngredient2 = meal["strIngredient2"] as? String,
                strIngredient3 = meal["strIngredient3"] as? String,
                strIngredient4 = meal["strIngredient4"] as? String,
                strIngredient5 = meal["strIngredient5"] as? String,
                strIngredient6 = meal["strIngredient6"] as? String,
                strIngredient7 = meal["strIngredient7"] as? String,
                strIngredient8 = meal["strIngredient8"] as? String,
                strIngredient9 = meal["strIngredient9"] as? String,
                strIngredient10 = meal["strIngredient10"] as? String,
                strIngredient11 = meal["strIngredient11"] as? String,
                strIngredient12 = meal["strIngredient12"] as? String,
                strIngredient13 = meal["strIngredient13"] as? String,
                strIngredient14 = meal["strIngredient14"] as? String,
                strIngredient15 = meal["strIngredient15"] as? String,
                strIngredient16 = meal["strIngredient16"] as? String,
                strIngredient17 = meal["strIngredient17"] as? String,
                strIngredient18 = meal["strIngredient18"] as? String,
                strIngredient19 = meal["strIngredient19"] as? String,
                strIngredient20 = meal["strIngredient20"] as? String,
                strMeasure1 = meal["strMeasure1"] as? String,
                strMeasure2 = meal["strMeasure2"] as? String,
                strMeasure3 = meal["strMeasure3"] as? String,
                strMeasure4 = meal["strMeasure4"] as? String,
                strMeasure5 = meal["strMeasure5"] as? String,
                strMeasure6 = meal["strMeasure6"] as? String,
                strMeasure7 = meal["strMeasure7"] as? String,
                strMeasure8 = meal["strMeasure8"] as? String,
                strMeasure9 = meal["strMeasure9"] as? String,
                strMeasure10 = meal["strMeasure10"] as? String,
                strMeasure11 = meal["strMeasure11"] as? String,
                strMeasure12 = meal["strMeasure12"] as? String,
                strMeasure13 = meal["strMeasure13"] as? String,
                strMeasure14 = meal["strMeasure14"] as? String,
                strMeasure15 = meal["strMeasure15"] as? String,
                strMeasure16 = meal["strMeasure16"] as? String,
                strMeasure17 = meal["strMeasure17"] as? String,
                strMeasure18 = meal["strMeasure18"] as? String,
                strMeasure19 = meal["strMeasure19"] as? String,
                strMeasure20 = meal["strMeasure20"] as? String,
                strSource = meal["strSource"] as? String,
                strImageSource = meal["strImageSource"] as? String,
                strCreativeCommonsConfirmed = meal["strCreativeCommonsConfirmed"] as? String,
                dateModified = meal["dateModified"] as? String,
            )

            mealsArr.add(singleMeal)

        }
    }

    private fun viewMealsInRecycleView() {
        val adapter = ResultsActivityAdaptor(this, mealsArr, this)
        recyclerView.adapter = adapter
    }

    private fun addAllMealsToDB() {
        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                mealDao.insertSetOfMeals(mealsArr)
            }
        }

        utilityRepo.makeToast("Meals Successfully Added", true)

    }

    override fun onMealItemClick(meal: Meal) {
        Log.i("selectMealIt", "Selected Meal: ${meal.strMeal}")

        val callMealOnClickPopUp = MealOnClickPopUp()
        callMealOnClickPopUp.mealDetailsPopUp(this, meal)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("userInput", userInput)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        utilityRepo.networkCheckAndGiveError()
        userInput = savedInstanceState.getString("userInput").toString()

        if (userInput != "") {
            val urlIngredientString =
                "https://www.themealdb.com/api/json/v1/1/filter.php?i=$userInput"
            readWebByIngredient(urlIngredientString)
            viewMealsInRecycleView()

            btnSaveMeals.isVisible = true
        }
    }

}