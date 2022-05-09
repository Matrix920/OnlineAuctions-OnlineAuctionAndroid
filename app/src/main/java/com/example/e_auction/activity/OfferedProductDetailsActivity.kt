package com.example.e_auction.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.e_auction.R
import com.example.e_auction.application.MyApplication
import com.example.e_auction.list_view.Product
import com.example.e_auction.list_view.ProductWithBids
import com.example.e_auction.list_view.User
import com.example.e_auction.session.APIs
import com.example.e_auction.session.SessionManager
import com.example.e_auction.utility.AuctionDialog
import com.example.e_auction.utility.Text
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_my_product_details.*
import kotlinx.android.synthetic.main.activity_my_product_details.txtInitialPrice
import kotlinx.android.synthetic.main.activity_my_product_details.txtProductName
import kotlinx.android.synthetic.main.activity_offered_product_details.*
import org.json.JSONObject

class OfferedProductDetailsActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    val TAG = OfferedProductDetailsActivity::class.java.simpleName
    var productID: Int = 0
    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offered_product_details)

        sessionManager = MyApplication.getInstance().getSession()
        sessionManager.ifUserLoggedOut(this)

        productID = intent.getIntExtra(Product.PRODUCT_ID, 0)

        getProduct()
        btnBid.setOnClickListener(View.OnClickListener {
            if(!Text.isEmpty(edtBid)) {
                if (edtBid.text.toString().toInt() < product.MaxBid) {
                    Toast.makeText(
                        this@OfferedProductDetailsActivity,
                        "You must enter a higer value",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    val params = HashMap<String, Any?>()
                    params.put(Product.PRODUCT_ID, productID)
                    params.put(User.USER_ID, sessionManager.UserID)
                    params.put(Product.OFFERED_PRICE, edtBid.text.toString().trim())

                    val requset =
                        JsonObjectRequest(Request.Method.POST, APIs.BID_PRODUCT, JSONObject(params), Response.Listener {
                            AuctionDialog.hideDialog()
                            if (it == null) {
                                Toast.makeText(
                                    this@OfferedProductDetailsActivity,
                                    "couldn't get response",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            val message = it.getBoolean(APIs.MESSAGE)
                            if (message)
                                Toast.makeText(
                                    this@OfferedProductDetailsActivity,
                                    "Bidded successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            else
                                Toast.makeText(
                                    this@OfferedProductDetailsActivity,
                                    "error try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            getProduct()

                        }, Response.ErrorListener {
                            AuctionDialog.hideDialog()
                            Toast.makeText(this@OfferedProductDetailsActivity, it!!.message, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, it!!.message)

                        })
                    AuctionDialog.showDialog(this@OfferedProductDetailsActivity, "Bidding")
                    MyApplication.getInstance().addToRequestQueue(requset)
                }
            }
        })

    }

    fun getProduct() {
        val params = HashMap<String, Any?>()
        params.put(Product.PRODUCT_ID, productID)
        val link=APIs.offeredProductDetails(productID)
        val requset =
            JsonObjectRequest(Request.Method.GET, link, JSONObject(params), Response.Listener {
                AuctionDialog.hideDialog()
                if (it == null) {
                    Toast.makeText(this@OfferedProductDetailsActivity, "couldn't get response", Toast.LENGTH_SHORT)
                        .show()
                }

                product =
                    Gson().fromJson<Product>(it.toString(), object : TypeToken<Product>() {}.type)
                displayProduct(product)

            }, Response.ErrorListener {
                AuctionDialog.hideDialog()
                Toast.makeText(this@OfferedProductDetailsActivity, it!!.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, it!!.message)

            })
        AuctionDialog.showDialog(this@OfferedProductDetailsActivity, "loading...")
        MyApplication.getInstance().addToRequestQueue(requset)
    }

    fun displayProduct(product: Product) {
        txtProductName.text = product.ProductName
        txtInitialPrice.text = product.InitialPrice.toString()
        txtMaxBidOffer.text=product.MaxBid.toString()
    }
}
