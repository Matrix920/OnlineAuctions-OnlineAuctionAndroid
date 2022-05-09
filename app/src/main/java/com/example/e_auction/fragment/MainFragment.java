package com.example.e_auction.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_auction.R;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MainFragment extends Fragment{

    protected ProgressBar pBar;
    //BaseAdapter adapter;
    View view;
    //final protected DataHandler dataHandler=DataHandler.getInstance();
    ImageButton btnRefresh;

    RecyclerView recyclerView;
    //SessionManager sessionManager= c

    //not used
   // RecyclerView recyclerView;
   // private static ProductsAdapter productsAdapter;
    //RequestQueue requestQueuee=AppController.getInstance().getRequestQueue();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
//        dataHandler.clear();
        super.onDestroy();
    }

    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view= inflater.inflate(R.layout.fragment_main_1,container,false);
        view= inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView=view.findViewById(R.id.recyclerViewProducts);


/*
        view= inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
*/
        pBar=view.findViewById(R.id.productsPogressBar);
        //btnRefresh=view.findViewById(R.id.btnRefresh);

        return view;
    }


/*
    protected void viewRefreshButton(){
        viewError("check internet connection");
        btnRefresh.setVisibility(View.VISIBLE);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRefresh.setVisibility(View.GONE);
                //getProducts();
            }
        });
    }
*/
    public Date getDate(String strDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd", Locale.US);
            return sdf.parse(strDate);
        }catch (Exception e){
            return null;
        }
    }

    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }



    protected void viewError(String msg){
        Toast.makeText(getContext(),msg, Toast.LENGTH_LONG).show();
    }
/*
    private void setOnItemClicked(){
        productsAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int product_id) {
                startProductActivity(product_id);
            }
        });
    }
*/


}
