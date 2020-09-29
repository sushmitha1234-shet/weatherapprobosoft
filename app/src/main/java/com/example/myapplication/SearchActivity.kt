package com.example.myapplication

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar = findViewById(R.id.searchtoolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val recyclersearchView = findViewById<View>(R.id.recyclersearchView) as RecyclerView
        val sun2 = intArrayOf(
            R.drawable.icon_mostly_sunny_small,
            R.drawable.icon_rain_small,
            R.drawable.icon_mostly_cloudy_small,
            R.drawable.icon_partly_cloudy_small,
            R.drawable.icon_thunderstorm_small,
            R.drawable.icon_clear_night_small

        )
        val place2= arrayOf("Udupi,Karnataka","Mangalore,Karnataka","Bangalore,Karnataka","Chennai,Tamil Nadu","Mumabi,Maharashtra","Pune,Maharashtra")
        val temp2 = arrayOf("31 \u00B0C", "29 \u00B0C", "32 \u00B0C", "30 \u00B0C", "31 \u00B0C","24 \u00B0C")
        val tempinfo2 = arrayOf("Mostly Sunny", "Rain", "Mostly Cloudy", "Partly Cloudy", "Thunderstorm","Cloudy")

        val lManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        recyclersearchView.layoutManager = lManager

        recyclersearchView.adapter = SearchAdapter(sun2, place2,temp2, tempinfo2,this)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_recent,menu)

        val manager = getSystemService(Context.SEARCH_SERVICE)as SearchManager
        val searchItem = menu?.findItem(R.id.recentsearchbar)
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

