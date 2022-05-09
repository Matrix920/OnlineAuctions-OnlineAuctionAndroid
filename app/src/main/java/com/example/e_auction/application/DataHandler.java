package com.example.e_auction.application;

import android.util.Log;


import java.util.List;

/**
 * Created by Matrix on 11/28/2018.
 */

public class DataHandler {
    private static DataHandler dataHandler;


    public static final String TAG=DataHandler.class.getSimpleName();

    private DataHandler(){

    }


    public static DataHandler getInstance(){
        if(dataHandler==null){
            Log.e(TAG,"initialize");
            dataHandler=new DataHandler();
            return dataHandler;

        }
        Log.e(TAG,"return");
        return dataHandler;
    }


}
