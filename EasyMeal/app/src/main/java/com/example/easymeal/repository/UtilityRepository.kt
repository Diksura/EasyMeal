package com.example.easymeal.repository

import android.content.Context
import android.widget.Toast

class UtilityRepository {
    fun makeToast(context: Context, message: String, isShort: Boolean){
        if (isShort) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}