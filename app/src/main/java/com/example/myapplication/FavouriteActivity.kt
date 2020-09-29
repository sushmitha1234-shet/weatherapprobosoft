package com.example.myapplication

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        val toolbar = findViewById(R.id.favtoolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val recyclerfavView = findViewById<View>(R.id.recyclerfavView) as RecyclerView
        val sun = intArrayOf(
            R.drawable.icon_mostly_sunny_small,
            R.drawable.icon_rain_small,
            R.drawable.icon_mostly_cloudy_small,
            R.drawable.icon_partly_cloudy_small,
            R.drawable.icon_thunderstorm_small,
            R.drawable.icon_clear_night_small

        )
        val place= arrayOf("Udupi,Karnataka","Mangalore,Karnataka","Bangalore,Karnataka","Chennai,Tamil Nadu","Mumabi,Maharashtra","Pune,Maharashtra")
        val temp = arrayOf("31 \u00B0C", "29 \u00B0C", "32 \u00B0C", "30 \u00B0C", "31 \u00B0C","24 \u00B0C")
        val tempinfo = arrayOf("Mostly Sunny", "Rain", "Mostly Cloudy", "Partly Cloudy", "Thunderstorm","Cloudy")

        val lManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        recyclerfavView.layoutManager = lManager

        recyclerfavView.adapter = FavouriteAdapter(sun, place,temp, tempinfo,this)



        val remove = findViewById<TextView>(R.id.tvRemoveAll)

        remove.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->

            }

            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_fav,menu)

        val manager = getSystemService(Context.SEARCH_SERVICE)as SearchManager
        val searchItem = menu?.findItem(R.id.favsearchbar)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("",false)
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true

    }

}