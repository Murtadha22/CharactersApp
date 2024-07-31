package com.example.retrofittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittest.databinding.FragmentFavouriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val characterViewModel: CharacterViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        characterViewModel.characters.observe(viewLifecycleOwner, Observer {
            adapter.updateList(FavoriteManager.getFavorites())
        })
    }

    private fun setupRecyclerView() {
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        adapter = CharacterAdapter(listOf(), { character ->
            characterViewModel.selectCharacter(character)
        }, { character ->
            FavoriteManager.addFavorite(character)
        })
        binding.categoryRv.adapter = adapter
    }
}
