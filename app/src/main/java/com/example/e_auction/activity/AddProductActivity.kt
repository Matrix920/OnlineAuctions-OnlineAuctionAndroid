package com.example.e_auction.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.e_auction.R
import com.example.e_auction.application.MyApplication
import com.example.e_auction.list_view.Product
import com.example.e_auction.list_view.User
import com.example.e_auction.session.APIs
import com.example.e_auction.session.SessionManager
import com.example.e_auction.utility.AuctionDialog
import com.example.e_auction.utility.Text
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class AddProductActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    val TAG=AddProductActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        sessionManager= MyApplication.getInstance().getSession()
        sessionManager.ifUserLoggedOut(this)

        btnAddProduct.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!Text.isEmpty(edtProductName, edtInitialPrice)) {
                    val params = HashMap<String, Any?>()
                    params.put(Product.PRODUCT_NAME, edtProductName.text.toString().trim())
                    params.put(Product.INITIAL_PRICE, edtInitialPrice.text.toString().trim())
                    params.put(User.USER_ID,sessionManager.UserID)

                    val requset =
                        JsonObjectRequest(Request.Method.POST, APIs.ADD_PRODUCT, JSONObject(params), Response.Listener {
                            AuctionDialog.hideDialog()
                            if (it == null) {
                                Toast.makeText(this@AddProductActivity, "couldn't get response", Toast.LENGTH_SHORT).show()
                            }

                            val message=it.getBoolean(APIs.MESSAGE)
                            if(message)
                                Toast.makeText(this@AddProductActivity,"Added successfully", Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(this@AddProductActivity,"Adding error try again", Toast.LENGTH_SHORT).show()
                            finish()

                        }, Response.ErrorListener {
                            AuctionDialog.hideDialog()
                            Toast.makeText(this@AddProductActivity, it!!.message, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, it!!.message)

                        })
                    AuctionDialog.showDialog(this@AddProductActivity, "Adding...")
                    MyApplication.getInstance().addToRequestQueue(requset)
                }
            }
        })
    }
}
