package com.example.e_auction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


import kotlinx.android.synthetic.main.activity_login.*
import com.example.e_auction.R
import com.example.e_auction.application.MyApplication
import com.example.e_auction.list_view.Product
import com.example.e_auction.list_view.User
import com.example.e_auction.session.APIs
import com.example.e_auction.session.SessionManager
import com.example.e_auction.utility.AuctionDialog
import com.example.e_auction.utility.Text


class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager:SessionManager
    val TAG=LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!Text.isEmpty(edtLogin, edtPassword)) {
                    val params:MutableMap<String,Any?> = HashMap()
                    params.put(User.LOGIN, edtLogin.text.toString().trim())
                    params.put(User.PASSWORD, edtPassword.text.toString().trim())

                    val requset =JsonObjectRequest(Request.Method.POST, APIs.LOGIN, JSONObject(params),Response.Listener {
                            if (it == null) {
                                Toast.makeText(this@LoginActivity, "couldn't get response", Toast.LENGTH_SHORT).show()
                            }

                            val user: User =
                                Gson().fromJson<User>(it.toString(), object : TypeToken<User>() {}.type)
                            sessionManager.login(this@LoginActivity, user.UserID)

                        }, Response.ErrorListener {
                            AuctionDialog.hideDialog()
                            Toast.makeText(this@LoginActivity, it!!.message, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, it!!.message)

                        })
                    AuctionDialog.showDialog(this@LoginActivity, "Authenticating")
                    MyApplication.getInstance().addToRequestQueue(requset)
                }
            }
        })

        btnRegister.setOnClickListener(View.OnClickListener {
            val i= Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(i)
        })
    }

    override fun onStart() {
        super.onStart()

        sessionManager = MyApplication.getInstance().getSession()

        sessionManager.checkLogin(this)
    }
}
