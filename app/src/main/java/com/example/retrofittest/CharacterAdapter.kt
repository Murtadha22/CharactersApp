package com.example.retrofittest

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.retrofittest.databinding.CharacterCardBinding
import com.example.retrofittest.models.Result

class CharacterAdapter(
    private var characters: List<Result>,
    private val onItemClick: (Result) -> Unit,
    private val onFavoriteClick: (Result) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(binding: CharacterCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.categoryImage
        private val nameView: TextView = binding.categoryName
        private val statusView: TextView = binding.categoryStatus
        private val genderView: TextView = binding.categoryGender
        private val speciesView: TextView = binding.categorySpecies
        private val favoriteBtn: CheckBox = binding.favoriteBtn

        fun bind(character: Result, onItemClick: (Result) -> Unit, onFavoriteClick: (Result) -> Unit) {
            nameView.text = character.name
            statusView.text = character.status
            genderView.text = character.gender
            speciesView.text = character.species
            imageView.load(character.image)
            itemView.setOnClickListener { onItemClick(character) }
            favoriteBtn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onFavoriteClick(character)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character, onItemClick, onFavoriteClick)
    }

    override fun getItemCount() = characters.size

    fun updateList(newCharacters: List<Result>) {
        characters = newCharacters
        notifyDataSetChanged()
    }
}
