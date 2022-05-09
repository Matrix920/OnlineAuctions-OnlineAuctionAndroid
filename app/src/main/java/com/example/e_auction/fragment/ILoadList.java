package com.example.e_auction.fragment;

import org.json.JSONObject;

/**
 * Created by Matrix on 11/28/2018.
 */

public interface ILoadList {

    public void getItems();
    public void extractAndViewItems(JSONObject jsonObject);
    public void startSecondActivity(int id);
    public void viewFab();
}
