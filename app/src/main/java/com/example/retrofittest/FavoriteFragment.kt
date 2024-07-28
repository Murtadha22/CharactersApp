package com.example.retrofittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittest.databinding.FragmentFavouriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        loadFavorites()
    }

    private fun setupRecyclerView() {
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        adapter = CharacterAdapter(FavoriteManager.getFavorites(), { character ->
            // Handle item click if needed
        }, { character ->
            // Handle favorite click if needed
        })
        binding.categoryRv.adapter = adapter
    }

    private fun loadFavorites() {
        adapter.updateList(FavoriteManager.getFavorites())
    }
}
