package com.alexlyxy.alextroikaone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexlyxy.alextroikaone.R
import com.alexlyxy.alextroikaone.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class DoCoWeAdapter : ListAdapter<DoCoWeModel, DoCoWeAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)
        private var itemDoCoWe: DoCoWeModel? = null

        /* init {
             itemView.setOnClickListener {
                 itemDoCoWe?.let { it1 -> listener?.onClick(it1) }
             }
         } */

        fun bind(item: DoCoWeModel) = with((binding)) {
            itemDoCoWe = item
            /* Picasso.get().load(item.dogFaceOne).into(ivDogOne)
              Picasso.get().load(item.dogFaceTwo).into(ivDogTwo)
              Picasso.get().load(item.dogFaceThree).into(ivDogThree)*/

            /*tvCName.text = item.coinName
            tvCFullName.text = item.coinFullName
            Picasso.get()
                .load("https://min-api.cryptocompare.com" + item.coinUrl + item.coinImageUrl)
                .into(ivCoin)
            // Picasso.get().load  (  "https://cdn.pixabay.com/photo/2019/04/15/20/42/bitcoin-4130299_1280.png").into(ivCoin)
            // "https://cdn.pixabay.com/photo/2019/04/15/20/42/bitcoin-4130299_1280.png"
            val coinUrl = "https://min-api.cryptocompare.com" + item.coinUrl + item.coinImageUrl
            Log.d("MyLog", "CoinUrl: $coinUrl")*/

            tvHoursDate.text = item.time
            tvCondit.text = item.condition
            tvTemp.text = item.currentTemp
            Picasso.get().load("https:" + item.imageURL).into(im)
        }
    }

    class Comparator : DiffUtil.ItemCallback<DoCoWeModel>() {
        override fun areItemsTheSame(oldItem: DoCoWeModel, newItem: DoCoWeModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DoCoWeModel, newItem: DoCoWeModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }


}