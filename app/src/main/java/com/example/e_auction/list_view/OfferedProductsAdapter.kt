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
import com.example.e_auction.activity.OfferedProductDetailsActivity

class OfferedProductsAdapter(var listProducts:ArrayList<Product>,val mContext: Context): RecyclerView.Adapter<OfferedProductsAdapter.OfferedProductViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferedProductsAdapter.OfferedProductViewHolder {
        val v= LayoutInflater.from(mContext).inflate(R.layout.item_offered_product,parent,false)

        return OfferedProductViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    override fun onBindViewHolder(holder: OfferedProductsAdapter.OfferedProductViewHolder, position: Int) {
        val product=listProducts[position]

        holder.productName.text=product.ProductName
        holder.initialPrice.text=product.InitialPrice.toString()+" $$"
        holder.username.text=product.Username
        holder.btnDetails.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //start my product details activity
                val i= Intent(mContext, OfferedProductDetailsActivity::class.java)
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

    class OfferedProductViewHolder(v: View): RecyclerView.ViewHolder(v){
        val productName: TextView
        val btnDetails: Button
        val username:TextView
        val initialPrice:TextView

        init {
            productName=v.findViewById(R.id.txtProductName)
            btnDetails=v.findViewById(R.id.btnOfferedProductDetails)
            username=v.findViewById(R.id.txtUsername)
            initialPrice=v.findViewById(R.id.txtInitialPrice)
        }
    }
}