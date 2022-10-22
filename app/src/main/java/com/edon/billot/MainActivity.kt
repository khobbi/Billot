/**
 * Developer: Hayford Edoonu (Edon)
 * Started: 16th December, 2021, 02:07pm
 *
 * Ended fist version(1.0): 20th December, 2021, 12:29pm
 * Aim: to solve problem of sharing bills where every data has to be gathered on paper
 * and calculated. This app will automate all the processes by just entering few items
 */

package com.edon.billot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.edon.billot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bnd: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bnd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bnd.root)

        //setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.tbarMain)
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.elevation = 4f
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //onclick listeners for the buttons
        bnd.btnUsers.setOnClickListener{
            startActivity(Intent(this, RegisteredUsersActivity::class.java))
        }
        bnd.btnDistBill.setOnClickListener {
            startActivity(Intent(this, DistributeBillActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuAbout -> {
                showAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog(){
        val alertDialog = AlertDialog.Builder(this) //create a builder

        //inflate the layout/create programmatical version of the layout
        val customLayout = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog_about, null)
        val btnClose = customLayout.findViewById<Button>(R.id.btnCloseDialog)

        alertDialog.setView(customLayout) // put the layout into the dialog

        val customDialog = alertDialog.create()
        customDialog.show()

        btnClose.setOnClickListener {
            customDialog.cancel()
        }
    }
}