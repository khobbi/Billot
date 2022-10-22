package com.edon.billot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.billot.databinding.ActivityRegisteredUsersBinding
import com.google.android.material.snackbar.Snackbar
import com.edon.billot.adapters.UsernamesAdapter
import com.edon.billot.databases.DatabaseHandler
import com.edon.billot.models.User

class RegisteredUsersActivity : AppCompatActivity() {
    lateinit var bnd: ActivityRegisteredUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityRegisteredUsersBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //getdata
        val dataSet = getDataset()

        //check if database is empty or has no users
        if(dataSet.size == 0){
            bnd.tvIfUsersEmpty.visibility = View.VISIBLE
            bnd.recShowUsers.visibility = View.GONE
        } else {
            bnd.tvIfUsersEmpty.visibility = View.GONE
            bnd.recShowUsers.visibility = View.VISIBLE

            val adapter = UsernamesAdapter(dataSet, this)

            //fix data and adapter into recycler view
            bnd.recShowUsers.layoutManager = LinearLayoutManager(this)
            bnd.recShowUsers.adapter = adapter
        }

        //onclick for fabAdd and button save
        bnd.fabAddUser.setOnClickListener { view ->
            startActivity(Intent(this, RegisterUserActivity::class.java))
            finish()
        }
    }

    //preparing data
    private fun getDataset(): ArrayList<User>{
        val dbHandler = DatabaseHandler.getInstance(this)
        return dbHandler.getAllNames()
    }

    //for editing user details
    fun editUser(user: User) {
        val intent = Intent(this, RegisterUserActivity::class.java)
        intent.putExtra("type", "update")
        intent.putExtra("userId", user.id)
        intent.putExtra("username", user.userName)
        intent.putExtra("userPoints", user.points)

        startActivity(intent)
        finish()
    }
}