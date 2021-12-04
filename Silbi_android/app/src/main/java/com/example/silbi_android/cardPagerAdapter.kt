package com.example.silbi_android

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.security.AccessController.getContext

class cardpagerAdapter(
    private val cards: List<card>
): RecyclerView.Adapter<  cardpagerAdapter. CardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.itemcard, parent, false)
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount() = cards.size


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cardname: TextView = itemView.findViewById(R.id.name)
        private val cardImage: ImageView = itemView.findViewById(R.id.image)
        private val cardPhone: TextView = itemView.findViewById(R.id.phone)
        private val cardfloor: TextView = itemView.findViewById(R.id.floor)

        fun bind(card: card) {

            val src = card.image

            cardname.text = card.name
            cardPhone.text = card.phone
            cardfloor.text = card.floor

            Glide.with(itemView).load(src).into(cardImage)
        }
    }
}