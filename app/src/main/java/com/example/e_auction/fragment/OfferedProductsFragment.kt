package com.example.e_auction.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.example.e_auction.R
import com.example.e_auction.application.MyApplication
import com.example.e_auction.list_view.OfferedProductsAdapter
import com.example.e_auction.list_view.Product
import com.example.e_auction.list_view.User
import com.example.e_auction.session.APIs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OfferedProductsFragnebt.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OfferedProductsFragnebt.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OfferedProductsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var searchView: SearchView

    private lateinit var v:View
    public lateinit var mAdapter:OfferedProductsAdapter
    private  lateinit var recyclerView:RecyclerView
    lateinit var listProducts:ArrayList<Product>
    val TAG=OfferedProductsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v=inflater.inflate(R.layout.fragment_main,container,false)

        setupRecyclerView()
//        setupSearchView()

        return v
    }

     fun setupRecyclerView() {
        listProducts=ArrayList()
        mAdapter= OfferedProductsAdapter(listProducts,v.context)

        val layoutManager=LinearLayoutManager(v.context)
        recyclerView=v.findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager=layoutManager
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.adapter=mAdapter
    }

    override fun onStart() {
        super.onStart()

        fetch()
    }

    fun fetch() {

        val link=APIs.offeredProducts(MyApplication.getInstance().getSession().UserID)
        val request= JsonArrayRequest(link,object: Response.Listener<JSONArray>{

            override fun onResponse(response: JSONArray?) {
                productsPogressBar.visibility=View.GONE
                if(response==null){
                    Toast.makeText(v.context,"couldn't get response", Toast.LENGTH_SHORT).show()
                    return
                }

                val products= Gson().fromJson<List<Product>>(response.toString(),object: TypeToken<List<Product>>(){}.type)

                listProducts.clear()
                listProducts.addAll(products)
                mAdapter.notifyDataSetChanged()

            }
        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                productsPogressBar.visibility=View.GONE
                Log.e(TAG,"Error: "+error?.message)
                Toast.makeText(v.context,"Error: "+error?.message, Toast.LENGTH_SHORT).show()
            }
        })

        productsPogressBar.visibility=View.VISIBLE
        MyApplication.getInstance().addToRequestQueue(request)
    }

    fun setupSearchView(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                val searchInput=query!!.toLowerCase()
                val filterListProducts:ArrayList<Product> = ArrayList()
                for( product:Product in listProducts){
                    if(product.ProductName.contains(searchInput))
                        filterListProducts.add(product)
                }
                mAdapter.updateList(filterListProducts)
                return true
            }
        })
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OfferedProductsFragnebt.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String,searchView: SearchView) =
            OfferedProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
                this.searchView=searchView
            }

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OfferedProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}
