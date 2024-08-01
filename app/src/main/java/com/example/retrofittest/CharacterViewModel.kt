package com.example.retrofittest

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofittest.databinding.FragmentHomeBinding
import com.example.retrofittest.models.getallcharacters.Result
import com.example.retrofittest.network.Constants
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val _characters = MutableLiveData<List<Result>>()
    val characters: LiveData<List<Result>> get() = _characters
    private val _selectedCharacter = MutableLiveData<Result>()
    val selectedCharacter: LiveData<Result> get() = _selectedCharacter
    private val _filteredCharacters = MutableLiveData<List<Result>>()
    val filteredCharacters: LiveData<List<Result>> get() = _filteredCharacters

    init {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        viewModelScope.launch {
            try {
                val response = Constants.retrofitService.getCharacter()
                if (response.isSuccessful) {
                    _characters.value = response.body()?.results ?: emptyList()
                    _filteredCharacters.value = _characters.value
                } else {
                    Log.i(Constants.TAG, "Response not successful")
                }
            } catch (e: Exception) {
                Log.i(Constants.TAG, "Exception: ${e.message}")
            }
        }
    }

    fun selectCharacter(character: Result) {
        _selectedCharacter.value = character
    }

    fun filterCharacters(query: String, binding: FragmentHomeBinding) {
        val filteredList = _characters.value?.filter {
            it.name.contains(query, true) ||
                    it.status.contains(query, true) ||
                    it.gender.contains(query, true) ||
                    it.species.contains(query, true)
        }
        _filteredCharacters.value = filteredList
    }
}