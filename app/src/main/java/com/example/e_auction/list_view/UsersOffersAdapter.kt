package com.example.e_auction.list_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_auction.R

class UsersOffersAdapter (var bidOffers:ArrayList<BidOffer>,val mContext: Context): RecyclerView.Adapter<UsersOffersAdapter.BidOfferViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersOffersAdapter.BidOfferViewHolder {
        val v= LayoutInflater.from(mContext).inflate(R.layout.item_user_bid,parent,false)

        return BidOfferViewHolder(v)
    }

    override fun getItemCount(): Int {
        return bidOffers.size
    }

    override fun onBindViewHolder(holder: UsersOffersAdapter.BidOfferViewHolder, position: Int) {
        val bo=bidOffers[position]

        holder.txtUsername.text=bo.Username
        holder.txtBidOffer.text=bo.OfferedPrice.toString()
    }

    class BidOfferViewHolder(v: View): RecyclerView.ViewHolder(v){
        val txtUsername: TextView
        val txtBidOffer:TextView

        init {
           txtUsername=v.findViewById(R.id.txtUsername)
            txtBidOffer=v.findViewById(R.id.txtBidOffer)
        }
    }
}