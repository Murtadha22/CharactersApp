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
    sealed class UIState {
        object Loading : UIState()
        data class Success(val data: List<Result>) : UIState()
        data class Error(val message: String) : UIState()
    }
    private val _characters = MutableLiveData<List<Result>>()
    val characters: LiveData<List<Result>> get() = _characters
    private val _selectedCharacter = MutableLiveData<Result>()
    val selectedCharacter: LiveData<Result> get() = _selectedCharacter
    private val _filteredCharacters = MutableLiveData<List<Result>>()
    val filteredCharacters: LiveData<List<Result>> get() = _filteredCharacters
    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> get() = _uiState

    init {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val response = Constants.retrofitService.getCharacter()
                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    _characters.value = results
                    _filteredCharacters.value = results
                    _uiState.value = UIState.Success(results)
                } else {
                    _uiState.value = UIState.Error("Response not successful")
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Exception: ${e.message}")
            }
        }
    }

    fun selectCharacter(character: Result) {
        _selectedCharacter.value = character
    }

    fun filterCharacters(query: String, binding: FragmentHomeBinding) {
        _uiState.value = UIState.Loading
        val filteredList = _characters.value?.filter {
            it.name.contains(query, true) ||
                    it.status.contains(query, true) ||
                    it.gender.contains(query, true) ||
                    it.species.contains(query, true)
        }
        _filteredCharacters.value = filteredList
        _uiState.value = UIState.Success(filteredList ?: emptyList())
    }
}