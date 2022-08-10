package com.example.halo

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ITRecyclerAdapter : RecyclerView.Adapter<ITRecyclerAdapter.DuyuruHolder>() {

    class DuyuruHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Duyuru>(){
        override fun areItemsTheSame(oldItem: Duyuru, newItem: Duyuru): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Duyuru, newItem: Duyuru): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var duyurular : List<Duyuru>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DuyuruHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return DuyuruHolder(view)
    }

    override fun onBindViewHolder(holder: DuyuruHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.textViewKonu)
        textView.text = "${duyurular.get(position).konu}"
        val cardView = holder.itemView.findViewById<CardView>(R.id.cardView)

        cardView.setOnClickListener {
            //textView.setTextColor(Color.BLACK)
            if(duyurular.get(position).alici == "IT"){
                val gecis = ITFragmentDirections.actionITFragmentToDetayFragment(duyurum = Duyuru(duyurular.get(position).alici
                    ,duyurular.get(position).konu
                    ,duyurular.get(position).duyuru))
                Navigation.findNavController(it).navigate(gecis)
            }else if(duyurular.get(position).alici == "İnsan Kaynakları"){
                val gecis = InsanKaynaklariFragmentDirections.actionInsanKaynaklariFragmentToDetayFragment(duyurum = Duyuru(duyurular.get(position).alici
                    ,duyurular.get(position).konu
                    ,duyurular.get(position).duyuru))
                Navigation.findNavController(it).navigate(gecis)
            }else{
                val gecis = MuhasebeFragmentDirections.actionMuhasebeFragmentToDetayFragment(duyurum = Duyuru(duyurular.get(position).alici
                    ,duyurular.get(position).konu
                    ,duyurular.get(position).duyuru))
                Navigation.findNavController(it).navigate(gecis)
            }

        }


    }

    override fun getItemCount(): Int {
        return duyurular.size
    }
}