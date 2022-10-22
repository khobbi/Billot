package com.edon.billot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.edon.billot.databases.DatabaseHandler
import com.edon.billot.databinding.ActivityRegisterUserBinding
import com.edon.billot.models.User

class RegisterUserActivity : AppCompatActivity() {
    lateinit var bnd: ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        val intent = intent
        var type = intent.getStringExtra("type").toString()
        val oldId = intent.getIntExtra("userId", 0)
        val oldPoints = intent.getIntExtra("userPoints", 0).toString()
        val oldUsername = intent.getStringExtra("username")

        if(type == "update"){
            bnd.edtUsername.setText(oldUsername)
            bnd.edtUserPoints.setText(oldPoints)
        } else{
            type = "insert"
        }
        bnd.btnSaveName.setOnClickListener {
            //submit empty user wich will be filled with those from edt
            saveAndUpdate(User(oldId), type)
        }
    }

    //save / update
    private fun saveAndUpdate(user: User, type: String){
        val dbHandler = DatabaseHandler.getInstance(this)
        val success: Long
        if (bnd.edtUserPoints.text.toString().isNotEmpty() && bnd.edtUsername.text.toString().isNotEmpty()){
            success = if(type == "insert"){
                dbHandler.addUsername(User(
                    0,
                    bnd.edtUsername.text.toString(),
                    bnd.edtUserPoints.text.toString().toInt()
                ))
            } else {
                dbHandler.updateUser(User(
                    user.id, //the old id
                    bnd.edtUsername.text.toString(),
                    bnd.edtUserPoints.text.toString().toInt()
                )).toLong()
            }

            if (success > -1){
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error Saving to Database", Toast.LENGTH_SHORT).show()
            }

            finish()
            startActivity(Intent(this, RegisteredUsersActivity::class.java))
        } else {
            Toast.makeText(this, "Input Data", Toast.LENGTH_SHORT).show()
        }
    }
}