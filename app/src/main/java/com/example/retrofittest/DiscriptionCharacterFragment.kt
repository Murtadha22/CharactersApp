package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.retrofittest.databinding.FragmentDiscriptionCharacterBinding

class DiscriptionCharacterFragment : Fragment() {
    private lateinit var binding: FragmentDiscriptionCharacterBinding
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscriptionCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterViewModel.selectedCharacter.observe(viewLifecycleOwner, Observer { character ->
            binding.apply {
                categoryImage.load(character.image)
                categoryName.text = character.name
                categoryStatus.text = character.status
                categoryGender.text = character.gender
                categorySpecies.text = character.species
            }
        })
    }
}
