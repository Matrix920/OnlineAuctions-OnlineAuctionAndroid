package com.example.e_auction.list_view

class Product (val ProductID:Int,val UserID:Int,val ProductName:String,val InitialPrice:Double,val IsSold:Boolean,val Username:String,val MaxBid:Double){
    companion object{
        val OFFERED_PRICE="OfferedPrice"
        val PRODUCT_ID="ProductID"
        val PRODUCT_NAME="ProductName"
        val INITIAL_PRICE="InitialPrice"
        val IS_SOLD="IsSold"
    }
}