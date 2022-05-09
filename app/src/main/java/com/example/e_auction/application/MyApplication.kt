package com.example.e_auction.application

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.e_auction.session.SessionManager

class MyApplication:Application() {

    val TAG=MyApplication::class.java.simpleName
    private  var mRequestQueue: RequestQueue?=null

    companion object {
        private lateinit var mInstance:MyApplication
        private lateinit var mSessionManager: SessionManager

        public @Synchronized fun  getInstance():MyApplication{
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance=this
        mSessionManager=SessionManager.getInstance(mInstance)
    }

    public fun getSession():SessionManager{
        return mSessionManager
    }

    public fun getRequestQueue():RequestQueue?{
        if(mRequestQueue == null)
            mRequestQueue=Volley.newRequestQueue(applicationContext)
        return mRequestQueue
    }

    public fun <T> addToRequestQueue(req:Request<T>,tag:String){
        if(TextUtils.isEmpty(tag))
            req.setTag(TAG)
        else
            req.setTag(tag)
        getRequestQueue()?.add(req)
    }

    public fun <T> addToRequestQueue(req:Request<T>){
        req.tag=TAG
        getRequestQueue()?.add(req)
    }

    public fun cancelPendingReqeusts(tag:Any){
        if(mRequestQueue!=null)
            mRequestQueue?.cancelAll(tag)
    }

}