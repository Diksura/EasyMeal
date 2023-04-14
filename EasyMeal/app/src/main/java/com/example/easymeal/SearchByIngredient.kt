package com.example.easymeal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.easymeal.database.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchByIngredient : AppCompatActivity() {

    var mealsArr = arrayListOf<Meal>()

    private lateinit var btnSearch: Button
    private lateinit var btnSaveMeals: Button
    private lateinit var edTxtSearchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_ingredient)

        btnSearch = findViewById(R.id.btnRetrieveMeals)
        btnSaveMeals = findViewById(R.id.btnSaveMeals)
        edTxtSearchBar = findViewById(R.id.inputSearchByIngredient)

//        btnSaveMeals.isVisible = false
//
//        btnSearch.setOnClickListener {  }
//
//        Log.i("testNet", "Network Stat : ${networkAvailability()}")
//        readFromWeb()

        networkNotAvailableError()
    }


    fun readFromWeb(){
        var stringBuilder = StringBuilder()

        var url_string = "https://www.themealdb.com/api/json/v1/1/search.php?s=rice"
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

        val tv: TextView = findViewById(R.id.tv)
        tv.setText(allMeals)
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
            dialog.setContentView(R.layout.network_erroe_popup)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()
        }
    }

}