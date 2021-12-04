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
                .inflate(R.layout.itemcard,parent,false)
        )

    override fun onBindViewHolder(holder:  CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount() = cards.size


    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        private val cardTextView1 : TextView = itemView.findViewById(R.id.name)
        private val cardImageView : ImageView = itemView.findViewById(R.id.imageView1)

        fun bind(card: card) {

            val src = card.texts2

            cardTextView1.text = card.texts

            Glide.with(itemView).load(src).into(cardImageView)
            cardTextView1.setMovementMethod(ScrollingMovementMethod())


        }
    }

}