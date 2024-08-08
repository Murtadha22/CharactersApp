package com.example.retrofittest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.retrofittest.data.Character
import com.example.retrofittest.data.CharacterDatabase
import com.example.retrofittest.data.network.Constants
import com.example.retrofittest.data.repo.CharacterRepository
import com.example.retrofittest.models.Location
import com.example.retrofittest.models.Origin
import com.example.retrofittest.models.Result
import kotlinx.coroutines.launch

class CharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val characterDao = CharacterDatabase.getDatabase(application).characterDao()
    private val repository: CharacterRepository = CharacterRepository(characterDao)

    sealed class UIState {
        object Loading : UIState()
        data class Success(val data: List<Result>) : UIState()
        data class Error(val message: String) : UIState()
    }

    val readAllData: LiveData<List<Character>> = repository.readAllData
    private val _characters = MutableLiveData<List<Result>>()
    val characters: LiveData<List<Result>> get() = _characters
    private val _selectedCharacter = MutableLiveData<Result>()
    val selectedCharacter: LiveData<Result> get() = _selectedCharacter
    private val _filteredCharacters = MutableLiveData<List<Result>>()
    val filteredCharacters: LiveData<List<Result>> get() = _filteredCharacters
    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> get() = _uiState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val response = Constants.retrofitService.getCharacter()
                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    _characters.value = results
                    _filteredCharacters.value = results
                    _uiState.value = UIState.Success(results)
                    // Save characters to Room database
                    results.forEach { character ->
                        val roomCharacter = Character(
                            id = character.id,
                            name = character.name,
                            status = character.status,
                            species = character.species,
                            gender = character.gender,
                            image = character.image,
                            type = character.type
                        )
                        repository.addCharacter(roomCharacter)
                    }
                } else {
                    fetchFromDatabase()
                }
            } catch (e: Exception) {
                e.message
                fetchFromDatabase()
            }
        }
    }///just test

    private fun fetchFromDatabase() {
        readAllData.observeForever {
            if (it.isNotEmpty()) {
                val results = it.map { character ->
                    Result(
                        id = character.id,
                        name = character.name,
                        status = character.status,
                        species = character.species,
                        gender = character.gender,
                        image = character.image,
                        type = character.type,
                        created = "",
                        episode = listOf(),
                        location = Location(name = "unknown", url = ""),
                        origin = Origin(name = "unknown", url = ""),
                        url = ""
                    )
                }
                _characters.value = results
                _filteredCharacters.value = results
                _uiState.value = UIState.Success(results)
            } else {
                _uiState.value = UIState.Error("No data available")
            }
        }
    }

    fun selectCharacter(character: Result) {
        _selectedCharacter.value = character
    }

    fun filterCharacters(query: String) {
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
