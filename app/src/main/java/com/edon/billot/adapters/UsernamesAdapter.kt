package com.edon.billot.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.edon.billot.R
import com.edon.billot.RegisteredUsersActivity
import com.edon.billot.databases.DatabaseHandler
import com.edon.billot.models.User

class UsernamesAdapter(private val dataSet: ArrayList<User>, private val context: Context):
    RecyclerView.Adapter<UsernamesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(R.id.tvUsername)
        val userPoints: TextView = itemView.findViewById(R.id.tvUserPoints)

        val wholeUserNameView: ConstraintLayout = itemView.findViewById(R.id.layoutUserName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.custom_layout_usernames, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = dataSet[position].userName
        holder.userPoints.text = dataSet[position].points.toString()

        holder.wholeUserNameView.setOnLongClickListener {
            /*if(context is RegisteredUsersActivity) {
                for (i in 0 until itemCount) {
                    if (dataSet[i].id == dataSet[position].id) {
                        editDeleteDialog(dataSet[i], i)
                        break
                    }
                }
            }*/

            editDeleteDialog(dataSet[position], position)
            false
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun editDeleteDialog(user: User, position: Int) {
        //creating dialog builder
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Edit/Delete User")
        dialogBuilder.setMessage("Edit or Delete ${user.userName}?")

        dialogBuilder.setPositiveButton("Edit"){ _, _ ->
            if(context is RegisteredUsersActivity)
            context.editUser(user)
        }

        dialogBuilder.setNegativeButton("Delete"){ _, _ ->
            val dbHandler = DatabaseHandler.getInstance(context)
            val success = dbHandler.deleteUser(user)
            if(success > -1){
                dataSet.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        dialogBuilder.setNeutralButton("Cancel"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }
}