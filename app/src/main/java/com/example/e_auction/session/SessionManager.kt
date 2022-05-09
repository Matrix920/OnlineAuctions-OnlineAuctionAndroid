package com.example.e_auction.session

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.e_auction.activity.LoginActivity
import com.example.e_auction.activity.MainActivity

class SessionManager private constructor(val context: Context) {

    private val sharedPref: SharedPreferences

    internal var editor: SharedPreferences.Editor

    val UserID: Int
        get() = sharedPref.getInt(USER_ID, 0)


    val SHARED_PREF_NAME = "e_auction"
    val PRIVATE_MODE = 0


    init {
        sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE)
        editor = sharedPref.edit()
    }

    companion object : SingletonHolder<SessionManager, Context>(::SessionManager)

    //logout
    fun clearAndLogout() {
        editor.clear()
        editor.commit()
    }

    fun login(activity: Activity, userID: Int) {

        editor.putInt(USER_ID, userID)
        editor.putBoolean(IS_LOGIN,true)
        editor.commit()
        startMainActivity(activity)


    }

    private fun startLoginActivity(activity: Activity) {

        val i = Intent(context, LoginActivity::class.java)
        //i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        i.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        i.putExtra("EXIT",true)
        context.startActivity(i)

        activity.finish()
    }

    fun checkLogin(activity: Activity) {
        val isLogin = sharedPref.getBoolean(IS_LOGIN, false)

        if (isLogin) {
                startMainActivity(activity)
        }
    }

    private fun startMainActivity(activity: Activity) {
        val i=Intent(activity,MainActivity::class.java)
        i.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
        activity.finish();

    }

    public fun ifUserLoggedOut(activity: Activity) {
        val isLogin = sharedPref.getBoolean(IS_LOGIN, false)

        if (!isLogin) {
            startLoginActivity(activity)
        }
    }


    fun logout(activity: Activity) {
        clearAndLogout()

        startLoginActivity(activity)
    }

    val USER_ID = "customerID"

    private val IS_LOGIN = "login"

}
