package com.example.easymeal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AndroidException
import android.util.AndroidRuntimeException
import android.util.Log
import android.view.Window
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

//    var mealsArr = mutableListOf<Meal>()
    val getMealsList = MealsList()
    var mealsArr =  getMealsList.mealsArr

    private lateinit var btnSearch: Button
    private lateinit var btnSaveMeals: Button
    private lateinit var edTxtSearchBar: EditText
    private lateinit var recyclerView: RecyclerView

    val utilityRepo = UtilityRepository()
    var userInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_ingredient)

        btnSearch = findViewById(R.id.btnRetrieveMeals)
        btnSaveMeals = findViewById(R.id.btnSaveMeals)
        edTxtSearchBar = findViewById(R.id.inputSearchByIngredient)
        recyclerView = findViewById(R.id.searchByIngRclView)


        btnSaveMeals.isVisible = false

        if (!networkAvailability()){
            networkNotAvailableError()
        }

        btnSearch.setOnClickListener {
            mealsArr.clear()
            getSearchName(edTxtSearchBar)

            viewMealsInRecycleView()

            btnSaveMeals.isVisible = true
        }

        btnSaveMeals.setOnClickListener {
            addAllMealsToDB()
        }
    }

    fun getSearchName(inputText: EditText){
        userInput = inputText.text.toString()
        val url_string =  "https://www.themealdb.com/api/json/v1/1/filter.php?i=$userInput"

        readWebByIngredient(url_string)
    }

    fun readWebByIngredient(url_string: String){
        val ingredientStringBuilder = StringBuilder()

        val urlIngredient = URL(url_string)
        val connectURL : HttpURLConnection = urlIngredient.openConnection() as HttpURLConnection

        runBlocking {
            launch {
                withContext(Dispatchers.IO){
                    var ingredientBufReader = BufferedReader(InputStreamReader(connectURL.inputStream))
                    var ingredientsLine: String? = ingredientBufReader.readLine()

                    while (ingredientsLine != null){
                        ingredientStringBuilder.append(ingredientsLine + "\n")
                        ingredientsLine = ingredientBufReader.readLine()
                    }

                    parseIngredientJSON(ingredientStringBuilder)
                }
            }
        }

    }

    fun parseIngredientJSON(stb: java.lang.StringBuilder){
        val jsonIngredient = JSONObject(stb.toString())
        val mealsIngredient = java.lang.StringBuilder()

        try{
            var ingredientJsonArray: JSONArray = jsonIngredient.getJSONArray("meals")

            mealsIngredient.append(ingredientJsonArray)

            var eachIngredient = ""
            for (ing in 0 until ingredientJsonArray.length()) {
                val ingredient: JSONObject = ingredientJsonArray[ing] as JSONObject

                eachIngredient = ingredient["idMeal"] as String
                readFromWeb(eachIngredient)
            }

        } catch (e: JSONException ){

        }
    }

    fun readFromWeb(eachIngredient: String){
        val stringBuilder = StringBuilder()

        val url_string = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$eachIngredient"
        val url = URL(url_string)
        val connect : HttpURLConnection = url.openConnection() as HttpURLConnection

        runBlocking {
            launch {
                withContext(Dispatchers.IO){
                    var bufReader = BufferedReader(InputStreamReader(connect.inputStream))
                    var line: String? = bufReader.readLine()

                    while (line!= null){
                        stringBuilder.append(line + "\n")
                        line = bufReader.readLine()
                    }
                    // adding parse method
                    parseJSON(stringBuilder)
                }
            }
        }

    }

    suspend fun parseJSON(stb: java.lang.StringBuilder){

        val json = JSONObject(stb.toString())
        val allMeals = java.lang.StringBuilder()

        var jsonArray: JSONArray = json.getJSONArray("meals")

        allMeals.append(jsonArray)
        Log.i("allBooks", allMeals.toString())


        //adding single data to Meal class
        for (i in 0 until jsonArray.length()){
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

    fun viewMealsInRecycleView(){
        val adapter = ResultsActivityAdaptor(this, mealsArr, this)
        recyclerView.adapter = adapter
    }

    fun addAllMealsToDB(){
        val db = Room.databaseBuilder(this, MealsDatabase::class.java, "mealsDatabase").build()
        val mealDao = db.mealDao()

        runBlocking {
            launch {
                mealDao.insertSetOfMeals(mealsArr)
            }
        }

        utilityRepo.makeToast(this,"Meals Successfully Added", true)

    }

    // For check network status if users is online of not
    @Suppress("DEPRECATION")
    private fun networkAvailability(): Boolean{
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnectedOrConnecting?:false
    }

    private fun networkNotAvailableError(){
        if (!networkAvailability()){
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.network_error_popup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val btnRetry: Button = dialog.findViewById(R.id.btnRetry)
            val btnBack: Button = dialog.findViewById(R.id.btnBack)

            btnRetry.setOnClickListener {
                if (networkAvailability()){
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }

    override fun onMealItemClick(meal: Meal) {
        Log.i("selectMealIt", "Selected Meal: ${meal.strMeal}")

        val callMealOnClickPopUp = MealOnClickPopUp()
        callMealOnClickPopUp.mealDetailsPopUp(this,meal)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("userInput", userInput)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        userInput = savedInstanceState.getString("userInput").toString()

        if (userInput != ""){
            val url_string = "https://www.themealdb.com/api/json/v1/1/filter.php?i=$userInput"
            readWebByIngredient(url_string)
            viewMealsInRecycleView()

            btnSaveMeals.isVisible = true
        }
    }

}