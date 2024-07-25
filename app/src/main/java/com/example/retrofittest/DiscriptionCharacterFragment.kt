package com.example.retrofittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.retrofittest.databinding.FragmentDiscriptionCharacterBinding


class DiscriptionCharacterFragment : Fragment() {

    private lateinit var binding: FragmentDiscriptionCharacterBinding
    private val args: DiscriptionCharacterFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscriptionCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val character = args.character
            categoryImage.load(character.image)
            categoryName.text = character.name
            categoryStatus.text = character.status
            categoryGender.text = character.gender
            categorySpecies.text = character.species
        }
    }


}