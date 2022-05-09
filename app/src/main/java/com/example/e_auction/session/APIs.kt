package com.example.e_auction.session

class APIs {
    companion object{
        val URL="http://eeauction.somee.com/Android/"

        val BID_PRODUCT=URL+"BidProduct"
        val OFFERED_PRODUCT_DETAILS=URL+"OtherProductDetails"
        val SOLD_PRODUCT=URL+"SoldProduct"
        val MY_PRODUCT_DETAILS=URL+"MyProductDetails"

        val REGISTER=URL+"Signup"

        val LOGIN=URL+"Login"

        val ADD_PRODUCT=URL+"AddProduct"
        val MESSAGE="Message"

        fun offeredProducts(userID: Int):String{
            return URL+"OthersProducts?UserID=$userID"
        }
        fun myProducts(userID:Int):String{
            return URL+"MyProducts?UserID=$userID"
        }

        fun myProductDetails(proudctID:Int):String{
            return MY_PRODUCT_DETAILS+"?ProductID=$proudctID"
        }

        fun soldProduct(productID:Int):String{
            return SOLD_PRODUCT+"?ProductID=$productID"
        }

        fun offeredProductDetails(productID: Int): String? {
            return OFFERED_PRODUCT_DETAILS+"?ProductID=$productID"
        }
    }
}