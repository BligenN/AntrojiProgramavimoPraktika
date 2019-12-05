package com.example.antrojiprogramavimopraktika.itemDatabase.shopRecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.antrojiprogramavimopraktika.R
import com.example.antrojiprogramavimopraktika.cartDatabase.CartEntity
import com.example.antrojiprogramavimopraktika.cartDatabase.cartRecyclerView.CartHolder
import com.example.antrojiprogramavimopraktika.itemDatabase.ItemEntity
import com.example.antrojiprogramavimopraktika.utils.Repository
import kotlinx.android.synthetic.main.item_view_shop.view.*

class ShopAdapter : RecyclerView.Adapter<CartHolder>() {

    private lateinit var context: Context
    private var dataList: List<ItemEntity> = listOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CartHolder {
        context = p0.context
        return CartHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_view_shop,
                p0,
                false
            )
        )

    }

    fun setData(dataList: List<ItemEntity>) {
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

        p0.itemView.button_purchase.setOnClickListener {
            Repository.getInstance()
                .saveCartItem(
                    CartEntity(
                        Repository.getInstance().getActiveUser(),
                        data.name, data.category, data.price
                    )
                )

            Toast.makeText(
                context,
                "Item successfully added to your shopping cart",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}