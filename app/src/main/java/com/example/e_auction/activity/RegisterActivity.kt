package com.example.e_auction.activity

import android.content.Intent
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
import com.example.e_auction.list_view.User
import com.example.e_auction.session.APIs
import com.example.e_auction.session.SessionManager
import com.example.e_auction.utility.AuctionDialog
import com.example.e_auction.utility.Text
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.edtLogin
import kotlinx.android.synthetic.main.activity_login.edtPassword
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    val TAG=RegisterActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sessionManager=MyApplication.getInstance().getSession()
        sessionManager.checkLogin(this)


        btnRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!Text.isEmpty(edtLogin, edtPassword,edtUsername,edtTel)) {
                    val params = HashMap<String, Any?>()
                    params.put(User.LOGIN, edtLogin.text.toString().trim())
                    params.put(User.PASSWORD, edtPassword.text.toString().trim())
                    params.put(User.TEL,edtTel.text.toString().trim())
                    params.put(User.USERNAME,edtUsername.text.toString().trim())

                    val requset =
                        JsonObjectRequest(Request.Method.POST, APIs.REGISTER, JSONObject(params), Response.Listener {
                            AuctionDialog.hideDialog()
                            if (it == null) {
                                Toast.makeText(this@RegisterActivity, "couldn't get response", Toast.LENGTH_SHORT).show()
                            }

                            val message=it.getBoolean(APIs.MESSAGE)
                            if(message)
                                Toast.makeText(this@RegisterActivity,"created successfully\nLogin with LOGIN & PASSWORD",Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(this@RegisterActivity,"creation error try again",Toast.LENGTH_SHORT).show()

                        }, Response.ErrorListener {
                            AuctionDialog.hideDialog()
                            Toast.makeText(this@RegisterActivity, it!!.message, Toast.LENGTH_SHORT).show()
                            Log.e(TAG, it!!.message)

                        })
                    AuctionDialog.showDialog(this@RegisterActivity, "Authenticating")
                    MyApplication.getInstance().addToRequestQueue(requset)
                }
            }
        })

    }

    public fun startLoginActivity(v:View){
        val i=Intent(this,LoginActivity::class.java)
        startActivity(i)
        finish()
    }


}
