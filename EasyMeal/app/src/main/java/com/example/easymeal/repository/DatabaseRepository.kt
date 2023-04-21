package com.example.easymeal.repository

import android.widget.EditText

class DatabaseRepository {

    public fun getSearchInputName(editText: EditText): String{
        val inputNm = editText.text.toString()
        editText.text.clear()

        return inputNm
    }

}