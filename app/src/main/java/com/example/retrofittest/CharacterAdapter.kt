package com.example.retrofittest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.retrofittest.databinding.CharacterCardBinding
import com.example.retrofittest.models.CharactersResponse
import com.example.retrofittest.models.Result


class CharacterAdapter(private val characters: List<Result>,
                       private val onItemClick: (Result) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {


    class CharacterViewHolder(binding: CharacterCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.categoryImage
        val nameView: TextView = binding.categoryName
        val statusView: TextView = binding.categoryStatus
        val genderView: TextView = binding.categoryGender
        val speciesView: TextView = binding.categorySpecies

        fun bind(character: Result, onItemClick: (Result) -> Unit) {
            nameView.text = character.name
            statusView.text = character.status
            genderView.text = character.gender
            speciesView.text = character.species
            imageView.load(character.image)
            itemView.setOnClickListener { onItemClick(character) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
       val binding = CharacterCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character, onItemClick)

    }

    override fun getItemCount() = characters.size
}
