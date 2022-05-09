package com.example.e_auction.list_view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_auction.R
import com.example.e_auction.activity.MyProductDetailsActivity
import kotlinx.android.synthetic.main.item_my_product.view.*

class MyProductsAdapter(var listProducts:ArrayList<Product>,val mContext: Context): RecyclerView.Adapter<MyProductsAdapter.MyProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductsAdapter.MyProductViewHolder {
        val v=LayoutInflater.from(mContext).inflate(R.layout.item_my_product,parent,false)

        return MyProductViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    override fun onBindViewHolder(holder: MyProductsAdapter.MyProductViewHolder, position: Int) {
        val product=listProducts[position]

        holder.productName.text=product.ProductName
        holder.btnDetails.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start my product details activity
                val i=Intent(mContext,MyProductDetailsActivity::class.java)
                i.putExtra(Product.PRODUCT_ID,product.ProductID)
                mContext.startActivity(i)
            }
        })
    }

    fun updateList(newlist:ArrayList<Product>){
        listProducts=ArrayList()
        listProducts.addAll(newlist)
        notifyDataSetChanged()
    }

    class MyProductViewHolder(v:View):RecyclerView.ViewHolder(v){
        val productName:TextView
        val btnDetails:Button

        init {
            productName=v.findViewById(R.id.txtProductName)
            btnDetails=v.findViewById(R.id.btnMyProductDetails)
        }
    }
}