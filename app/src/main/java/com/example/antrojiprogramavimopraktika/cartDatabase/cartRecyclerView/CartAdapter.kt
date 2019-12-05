package com.example.antrojiprogramavimopraktika.cartDatabase.cartRecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.cartDatabase.CartEntity
import com.example.antrojiprogramavimopraktika.utils.Repository
import kotlinx.android.synthetic.main.item_view_cart.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CartAdapter : RecyclerView.Adapter<CartHolder>() {

    private lateinit var context: Context
    private var dataList: List<CartEntity> = listOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CartHolder {
        context = p0.context
        return CartHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_view_cart,
                p0,
                false
            )
        )

    }

    fun setData(dataList: List<CartEntity>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(p0: CartHolder, p1: Int) {
        val data = dataList[p1]

        val itemNameTextView = p0.itemView.text_view_item_name
        val itemPriceTextView = p0.itemView.text_view_price

        itemNameTextView.text = data.name
        val priceText = "${data.price} €"
        itemPriceTextView.text = priceText

        p0.itemView.button_remove.setOnClickListener {
            CoroutineScope(IO).launch {
                Repository.getInstance().deleteCartItem(data)
            }
            p0.itemView.isVisible = false
        }
    }
}