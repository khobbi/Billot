package com.edon.billot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.edon.billot.adapters.ShowUserDetailsAdapter
import com.edon.billot.databinding.ActivityShowBillsBinding
import com.edon.billot.models.User

class ShowBillsActivity : AppCompatActivity() {
    lateinit var bnd: ActivityShowBillsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityShowBillsBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        val intent = intent
        val totalBill = intent.getFloatExtra("totalBill", 0.0f)
        val totalPoints = intent.getIntExtra("totalPoints", 0)
        val sharedPoints = intent.getIntExtra("sharedPoints", 0)
        val totalSharedBill = intent.getFloatExtra("totalSharedBill", 0.0f)
        val sharedBillIndiv = intent.getFloatExtra("SharedBillIndiv", 0.0f)

        //get array extras
        val allNames = intent.getStringArrayListExtra("allNames")
        val allPoints = intent.getStringArrayListExtra("allPoints")
        val allBills = intent.getStringArrayListExtra("allBills")

        val dataSet = ArrayList<User>()

        //pack the arrays into User (type) and use as dataSet
        //they will have the same size cos each index is for a single user
        if (allNames != null) {
            for(i in 0 until allNames.size){
                dataSet.add(User(0, allNames[i], allPoints?.get(i)!!.toInt(), allBills?.get(i)!!.toFloat()))
            }
        }

        //set up recycler view
        val adapter = ShowUserDetailsAdapter(dataSet, this)
        bnd.recShowBills.layoutManager = LinearLayoutManager(this)
        bnd.recShowBills.adapter = adapter

        //bottom info
        bnd.tvTotalBill.text = totalBill.toString()
        bnd.tvTotalPoints.text = totalPoints.toString()
        bnd.tvSharedPoints.text = sharedPoints.toString()
        bnd.tvTotalSharedBill.text = totalSharedBill.toString()
        bnd.tvIndividualSharedBill.text = sharedBillIndiv.toString()
    }
}