package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittest.databinding.FragmentHomeBinding
import com.example.retrofittest.models.getallcharacters.CharactersResponse
import com.example.retrofittest.models.getallcharacters.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var allCharacters: List<Result> = listOf()
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        getAllCharacters()
    }

    private fun setupRecyclerView() {
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        adapter = CharacterAdapter(allCharacters, { character ->
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
                filterCharacters(newText)
                return true
            }
        })
    }

    private fun getAllCharacters() {
        Constants.retrofitService.getCharacter().enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(
                call: Call<CharactersResponse>,
                response: Response<CharactersResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { results ->
                        allCharacters = results
                        adapter.updateList(allCharacters)
                    }
                } else {
                    Log.i(Constants.TAG, "Response not successful")
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Log.i(Constants.TAG, "inFailure: ${t.message}")
            }
        })
    }

    private fun filterCharacters(query: String?) {
        val filteredList = if (!query.isNullOrEmpty()) {
            allCharacters.filter {
                it.name.contains(query, true) ||
                        it.status.contains(query, true) ||
                        it.gender.contains(query, true) ||
                        it.species.contains(query, true)
            }
        } else {
            allCharacters
        }
        adapter.updateList(filteredList)
    }

    private fun navigateToCharacterDescription(character: Result) {
        val action = HomeFragmentDirections.actionHomeFragmentToDiscriptionCharacterFragment(character)
        findNavController().navigate(action)
    }
}
