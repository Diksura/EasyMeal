package com.example.easymeal.repository

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.easymeal.R

class UtilityRepository(val context: Context) {

    // For check network status if users is online of not
    fun networkCheckAndGiveError(){
        if (!networkAvailability()){
            networkNotAvailableError()
        }
    }
    @Suppress("DEPRECATION")
    private fun networkAvailability(): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnectedOrConnecting?:false
    }

    private fun networkNotAvailableError(){
        if (!networkAvailability()){
            val dialog = Dialog(context)
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

    fun makeToast(message: String, isShort: Boolean){
        if (isShort) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}