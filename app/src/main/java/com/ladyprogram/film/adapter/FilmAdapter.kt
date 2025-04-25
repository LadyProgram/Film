package com.ladyprogram.film.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ladyprogram.film.data.Film
import com.ladyprogram.film.databinding.ItemFilmBinding
import com.squareup.picasso.Picasso

class FilmAdapter (var items: List<Film>, val onClick: (Int) -> Unit) : Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size
    //{ return items.size }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = items[position]
        holder.render(film)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

}


class FilmViewHolder(val binding: ItemFilmBinding) : ViewHolder(binding.root) {

    //val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    //val pictureImageView: ImageView = view.findViewById(R.id.pictureImageView)

    fun render(film: Film) {
        binding.nameTextView.text = film.Title
        Picasso.get().load(film.Poster.url).into(binding.posterImageView)
    }

}