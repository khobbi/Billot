package com.edon.billot.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edon.billot.R
import com.edon.billot.models.User

class ShowUserDetailsAdapter(private val dataSet: ArrayList<User>, private val context: Context):
    RecyclerView.Adapter<ShowUserDetailsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val username: TextView = itemView.findViewById(R.id.tvShowUsername)
        val userpoints: TextView = itemView.findViewById(R.id.tvUserPoints)
        val userbill: TextView = itemView.findViewById(R.id.tvShowUserBill)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.custom_user_detail_layout,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = dataSet[position].userName
        holder.userpoints.text = dataSet[position].points.toString()
        holder.userbill.text = dataSet[position].bill.toString()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}