package com.example.retrofittest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittest.CharacterAdapter
import com.example.retrofittest.CharacterViewModel
import com.example.retrofittest.FavoriteManager
import com.example.retrofittest.databinding.FragmentHomeBinding
import com.example.retrofittest.models.Result

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val characterViewModel: CharacterViewModel by viewModels()

    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()

        characterViewModel.filteredCharacters.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
            binding.loadingBar.visibility = View.GONE
        })

        characterViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is CharacterViewModel.UIState.Loading -> binding.loadingBar.visibility = View.VISIBLE
                is CharacterViewModel.UIState.Success -> binding.loadingBar.visibility = View.GONE
                is CharacterViewModel.UIState.Error -> {
                    binding.loadingBar.visibility = View.GONE
                    showError(uiState.message)
                }
            }
        })
    }


    private fun setupRecyclerView() {
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        adapter = CharacterAdapter(listOf(), { character ->
            characterViewModel.selectCharacter(character)
            navigateToCharacterDescription(character)
        }, { character ->
            FavoriteManager.addFavorite(character)
        })
        binding.categoryRv.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                characterViewModel.filterCharacters(newText ?: "",binding)
                return true
            }
        })
    }

    private fun navigateToCharacterDescription(character: Result) {
        val action = HomeFragmentDirections.actionHomeFragmentToDiscriptionCharacterFragment(character)
        findNavController().navigate(action)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
