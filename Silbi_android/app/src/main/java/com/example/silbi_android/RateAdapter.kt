package com.example.silbi_android
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.rate

class RateAdapter(val context: Context, val shopList: ArrayList<rate>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_rate,null)//view붙이기 위함

        val name = view.findViewById<TextView>(R.id.nametext)
        val call = view.findViewById<TextView>(R.id.calltext)
        val floor = view.findViewById<TextView>(R.id.floortext)
        val diceImage = view.findViewById<ImageView>(R.id.imageView)


        val rate = shopList[position]
        name.text = rate.name
        call.text = rate.call
        floor.text = rate.floor
        Glide.with(context).load(rate.url).into(diceImage)
        return view
    }

    override fun getItem(position: Int): Any {

        return shopList[position]

    }

    override fun getCount(): Int {
        return shopList.size
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

}