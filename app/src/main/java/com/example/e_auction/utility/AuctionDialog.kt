package com.example.e_auction.utility

import android.app.ProgressDialog
import android.content.Context

public class AuctionDialog{


    companion object{

        lateinit var progressDialog:ProgressDialog

        fun showDialog(context: Context,message:String){
            progressDialog=ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.show();
        }

        fun hideDialog(){
            if(progressDialog.isShowing)
                progressDialog.hide();
        }


    }
}