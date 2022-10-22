package com.edon.billot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.edon.billot.databases.DatabaseHandler
import com.edon.billot.databinding.ActivityDistributeBillBinding
import com.edon.billot.models.User

class DistributeBillActivity : AppCompatActivity() {
    lateinit var bnd: ActivityDistributeBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityDistributeBillBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //first get all user data
        val allUsers = getUsers()

        bnd.btnDistributeBill.setOnClickListener {
            if(bnd.edtTotalBill.text.toString().isNotEmpty()){
                //get and store the shared points
                var sharedPoints = if(bnd.edtSharedPoints.text.toString().isEmpty()){
                    0 // set to 0 if empty
                } else{
                    bnd.edtSharedPoints.text.toString().toInt()
                }

                var totalBill = bnd.edtTotalBill.text.toString().toFloat()

                //do all calculations in the method
                calculateBills(allUsers, sharedPoints, totalBill)
            } else {
                Toast.makeText(this, "Total bill needed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //get users
    private fun getUsers(): ArrayList<User>{
        val dbHandler = DatabaseHandler.getInstance(this)
        return dbHandler.getAllNames()
    }

    //handle calculations
    private fun calculateBills(allUsers: ArrayList<User>, sharedPoints: Int, totalBill: Float){
        //get needed variables
        val totalUsers = allUsers.size
        var totalPoints = 0
        for(i in 0 until allUsers.size){
            totalPoints += allUsers[i].points
        }
        totalPoints += sharedPoints // add the shared point to the total points of users

        val sharedBill = String.format("%.2f", ((sharedPoints.toFloat() / totalPoints.toFloat())) * totalBill).toFloat()
        val sharedBillForEachUser = String.format("%.2f", (sharedBill / totalUsers.toFloat())).toFloat()

        //pack all usernames, bill, points into arrays and send them along with intent
        val arrayNames = ArrayList<String>()
        val arrayPoints = ArrayList<String>()
        val arrayBills = ArrayList<String>()

        //calculate each user's bill
        for(i in 0 until allUsers.size){
            allUsers[i].bill = ((allUsers[i].points.toFloat() / totalPoints.toFloat()) * totalBill) + sharedBillForEachUser

            //accumulate the arrays to be sent to intent
            //converting numbers to strings for easy display as no
            // calculations will be done with them
            arrayNames.add(allUsers[i].userName)
            arrayPoints.add(allUsers[i].points.toString())
            arrayBills.add(String.format("%.2f", allUsers[i].bill))
        }

        //start new activity and pass this to adapter
        val intent = Intent(this, ShowBillsActivity::class.java)
        intent.putExtra("totalBill", totalBill)
        intent.putExtra("totalPoints", totalPoints)
        intent.putExtra("sharedPoints", sharedPoints)
        intent.putExtra("totalSharedBill", sharedBill)
        intent.putExtra("SharedBillIndiv", sharedBillForEachUser)

        //send arrays as array extras
        intent.putExtra("allNames", arrayNames)
        intent.putExtra("allPoints", arrayPoints)
        intent.putExtra("allBills", arrayBills)

        startActivity(intent)
    }
}