package com.example.android_party.serverDisplay.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android_party.R
import com.example.android_party.serverDisplay.serverDatabase.ServerEntity
import kotlinx.android.synthetic.main.item_view.view.*

class MyAdapter : RecyclerView.Adapter<MyHolder>() {

    private lateinit var context: Context
    private var dataList: List<ServerEntity> = listOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        context = p0.context
        return MyHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_view,
                p0,
                false
            )
        )

    }

    fun setData(dataList: List<ServerEntity>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        val data = dataList[p1]

        val serverNameTextView = p0.itemView.serverName
        val serverDistanceTextView = p0.itemView.serverDistance

        serverNameTextView.text = data.serverName
        val distanceText = "${data.serverDistance} km"
        serverDistanceTextView.text = distanceText

        p0.itemView.setOnClickListener {
            Toast.makeText(context, data.serverName, Toast.LENGTH_SHORT).show()
        }
    }
}