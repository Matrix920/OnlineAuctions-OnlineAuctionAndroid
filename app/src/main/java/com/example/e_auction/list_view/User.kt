package com.example.e_auction.list_view

class User(val UserID:Int,val Password:String,val Login:String,val Tel:Long,val Username:String){
    companion object {
        val LOGIN = "Login"
        val PASSWORD = "Password"
        val USERNAME="Username"
        val TEL = "Tel"
        val USER_ID = "UserID"
    }
}