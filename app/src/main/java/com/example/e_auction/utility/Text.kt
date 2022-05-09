package com.example.e_auction.utility

import android.text.TextUtils
import android.widget.EditText

class Text {
    companion object{
        fun isEmpty(vararg edts: EditText):Boolean{
            for (edt:EditText in edts){
                if(TextUtils.isEmpty(edt.text))
                    return true
            }

            return false
        }

        fun getString(edt:EditText):String{
            return edt.text.toString().trim()
        }
    }
}