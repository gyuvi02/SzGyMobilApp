package com.gyula.kepek

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso


class KepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class KepViewAdapter(private var kepLista : List<Kep>) : RecyclerView.Adapter<KepViewHolder>() {

    override fun getItemCount(): Int {
        return if (kepLista.isNotEmpty()) kepLista.size else 1
    }

    fun loadNewData(newPhotos: List<Kep>) {
        kepLista = newPhotos
        notifyDataSetChanged()
    }

    fun getKep(position: Int): Kep? {
        return if (kepLista.isNotEmpty()) kepLista[position] else null
    }

    override fun onBindViewHolder(holder: KepViewHolder, position: Int) = if (kepLista.isEmpty()) {
        holder.thumbnail.setImageResource(R.drawable.placeholder)
        holder.title.setText(R.string.empty_photo)
    } else {
        val kepItem = kepLista[position]
        Picasso.with(holder.thumbnail.context).load(kepItem.thumbnailUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail)

        holder.title.text = "Cím: \n" + kepItem.title.toString()
        Toast.makeText(holder.title.context, "Kattints a képekre a nagyításért", Toast.LENGTH_LONG).show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KepViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return KepViewHolder(view)
    }
}