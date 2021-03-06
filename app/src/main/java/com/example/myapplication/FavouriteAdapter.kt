package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavouriteAdapter (private val sun: IntArray,private val place : Array<String>, private val temp: Array<String>, private val tempinfo:Array<String>,private val mContext: Context):
    RecyclerView.Adapter<FavouriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.cardview2,parent,false)
        return FavouriteHolder(v,mContext)
    }

    override fun getItemCount(): Int {
        return sun.size
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        holder?.index(sun[position],place[position],temp[position],tempinfo[position])
    }


}
