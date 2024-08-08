package com.example.retrofittest.data.repo

import androidx.lifecycle.LiveData
import com.example.retrofittest.data.Character
import com.example.retrofittest.data.CharacterDao

class CharacterRepository(private val characterDao: CharacterDao) {


    val readAllData: LiveData<List<Character>> = characterDao.getAllCharacters()

     fun addCharacter(character: Character){
        characterDao.addCharacter(character)
    }
}