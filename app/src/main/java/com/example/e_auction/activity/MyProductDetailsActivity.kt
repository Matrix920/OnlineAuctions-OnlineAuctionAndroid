package com.example.e_auction.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.e_auction.R
import com.example.e_auction.application.MyApplication
import com.example.e_auction.list_view.*
import com.example.e_auction.session.APIs
import com.example.e_auction.session.SessionManager
import com.example.e_auction.utility.AuctionDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_my_product_details.*
import org.json.JSONObject

class MyProductDetailsActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    val TAG = MyProductDetailsActivity::class.java.simpleName
    var productID: Int = 0
    private lateinit var mAdapter:UsersOffersAdapter
//    private  lateinit var recyclerView: RecyclerView
    lateinit var listUsersBids:ArrayList<BidOffer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_product_details)

        sessionManager = MyApplication.getInstance().getSession()
        sessionManager.ifUserLoggedOut(this)

        setupRecyclerView()

        productID = intent.getIntExtra(Product.PRODUCT_ID, 0)

        getProduct()
        btnSold.setOnClickListener(View.OnClickListener {
            val params = HashMap<String, Any?>()
            params.put(Product.PRODUCT_ID, productID)
            val link=APIs.soldProduct(productID)
            val requset =
                JsonObjectRequest(Request.Method.GET, link, JSONObject(params), Response.Listener {
                    AuctionDialog.hideDialog()
                    if (it == null) {
                        Toast.makeText(this@MyProductDetailsActivity, "couldn't get response", Toast.LENGTH_SHORT).show()
                    }

                    val message=it.getBoolean(APIs.MESSAGE)
                    if(message)
                        Toast.makeText(this@MyProductDetailsActivity,"Set Sold successfully",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this@MyProductDetailsActivity,"error try again",Toast.LENGTH_SHORT).show()
                    getProduct()

                }, Response.ErrorListener {
                    AuctionDialog.hideDialog()
                    Toast.makeText(this@MyProductDetailsActivity, it!!.message, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, it!!.message)

                })
            AuctionDialog.showDialog(this@MyProductDetailsActivity, "Saving...")
            MyApplication.getInstance().addToRequestQueue(requset)
        })
    }

    fun getProduct() {
        val params = HashMap<String, Any?>()
        params.put(Product.PRODUCT_ID, productID)
        val link=APIs.myProductDetails(productID)
        val requset =
            JsonObjectRequest(Request.Method.GET, link, JSONObject(params), Response.Listener {
                AuctionDialog.hideDialog()
                if (it == null) {
                    Toast.makeText(this@MyProductDetailsActivity, "couldn't get response", Toast.LENGTH_SHORT).show()
                }

                val product: ProductWithBids =
                    Gson().fromJson<ProductWithBids>(it.toString(), object : TypeToken<ProductWithBids>() {}.type)
                displayProduct(product)

            }, Response.ErrorListener {
                AuctionDialog.hideDialog()
                Toast.makeText(this@MyProductDetailsActivity, it!!.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, it!!.message)

            })
        AuctionDialog.showDialog(this@MyProductDetailsActivity, "loading...")
        MyApplication.getInstance().addToRequestQueue(requset)
    }

    fun displayProduct(product:ProductWithBids){
        if(product.IsSold)
            imgIsSold.setImageResource(R.mipmap.sold)
        txtProductName.text=product.ProductName
        txtInitialPrice.text=product.InitialPrice.toString()
        if(product.IsSold)
            btnSold.isEnabled=false

        listUsersBids.clear()
        listUsersBids.addAll(product.BidOffers)
        mAdapter.notifyDataSetChanged()
    }

    fun setupRecyclerView() {
        listUsersBids=ArrayList()
        mAdapter= UsersOffersAdapter(listUsersBids,this)

        val layoutManager= LinearLayoutManager(this)
        recyclerViewUsersBids.layoutManager=layoutManager
        recyclerViewUsersBids.itemAnimator= DefaultItemAnimator()
        recyclerViewUsersBids.adapter=mAdapter
    }
}
