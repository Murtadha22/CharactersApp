package com.example.retrofittest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(private val characters: List<Result>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.category_image)
        val nameView: TextView = view.findViewById(R.id.category_name)
        val statusView: TextView = view.findViewById(R.id.category_status)
        val genderView: TextView = view.findViewById(R.id.category_gender)
        val speciesView: TextView = view.findViewById(R.id.category_species)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_card, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.nameView.text = character.name
        holder.statusView.text = character.status
        holder.genderView.text = character.gender
        holder.speciesView.text = character.species
        Glide.with(holder.imageView.context).load(character.image).into(holder.imageView)
    }

    override fun getItemCount() = characters.size
}
