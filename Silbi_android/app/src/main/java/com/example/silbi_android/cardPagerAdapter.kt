package com.example.silbi_android

import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
        private val cardImage: ImageView = itemView.findViewById(R.id.imageView1)
        private val cardPhone: TextView = itemView.findViewById(R.id.phone)
        private val cardfloor: TextView = itemView.findViewById(R.id.floor)
        private val button: Button = itemView.findViewById<Button>(R.id.btn1)

        fun bind(card: card) {

            val src = card.image
            val name = card.name
            cardname.text = card.name
            cardPhone.text = card.phone
            cardfloor.text = card.floor

            if (src == "_") {
                Glide.with(itemView).load("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACgCAMAAACBpqFQAAAAMFBMVEXu7u6ZmZmurq7Z2dnDw8Pe3t7Ozs6+vr6enp7p6enJycmpqanj4+Ojo6O5ubmzs7NLxFgzAAAB2klEQVR4nO3X3XLCIBCGYZa/BEjM/d9tWYgprXbGHtS25n0OVCLj6De7YTUGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPAvTHZc2WlYRBs+7w77pZS9dz/7xf4iK+NKxui8XPMIm8yXpb5w/VKYpRqDfW0p+qjPGla0TehhTW2Rh7BqNmWVko6wVokprHJTeC+q1UZZelheGtfDsm0Rh7AmXV2ktt6lX5JSH7L4X/wBz7RKXqYezZ6Ku4bV316GsKzUoopi90SNmbWorMTf+vbP5doNp8gXYWW5mCEsX3cne2zS4KRIK68ziK2Ftlo/98IKvYCOsNIstWs3c4Rl3NbuaucQNJWawd3K0vvZHMbTME17NrrJj07RiVY2X7S8eljZuXwNK84So6b1HtZB5ywZ2Xsf/mrSVn+ptuLNaVhzitqn6xiWjq7T1MdUNzrN9NAeNSzXW2rplZV1+jTxQ2XpLmvNPjmYPpfZ80ylu4cm+D572Xp8pr5t1qzO0YOjR8PyfrWLtJHiuo2whsVNGxZZe1pS9H5FWMPicxvWv86ptGtnOgq/L+oBkJ3+/9blmYYsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA4EneAHYKB+xqekqbAAAAAElFTkSuQmCC").into(cardImage)
            } else {
                Glide.with(itemView).load(src).into(cardImage)
            }

            button.setOnClickListener {


                var email = ""
                var id = ""
                val user = Firebase.auth.currentUser
                if (user != null) {
                    Log.d("user",user.toString())
                    email = user!!.email.toString()
                    val splitArray = email.split("@")
                    id = splitArray[0]
                } else {
                    Log.d("user","정보 가져올 수 없음")
                }

                val database: FirebaseDatabase =
                    FirebaseDatabase.getInstance("https://silbi-7becf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                val myRef1: DatabaseReference = database.getReference("$id/점포/$name/name")
                myRef1.setValue(card.name)
                val myRef2: DatabaseReference = database.getReference("$id/점포/$name/image")
                myRef2.setValue(src)
                val myRef3: DatabaseReference = database.getReference("$id/점포/$name/phone")
                myRef3.setValue(card.phone)
                val myRef4: DatabaseReference = database.getReference("$id/점포/$name/floor")
                myRef4.setValue(card.floor)

                val builder = AlertDialog.Builder(itemView.context)
                builder.setTitle("완료")
                builder.setMessage("마이페이지에 저장되었습니다!")
                builder.setNeutralButton("확인", null)
                builder.show()
            }

        }


    }
}