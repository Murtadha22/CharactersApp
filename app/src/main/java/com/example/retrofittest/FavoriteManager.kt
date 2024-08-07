package com.example.retrofittest

<<<<<<< HEAD
import com.example.retrofittest.models.Result
=======
import com.example.retrofittest.models.getallcharacters.Result
>>>>>>> 35fb2d908091b31c3996525698ceaf0e9fb3c6b3

object FavoriteManager {
    private val favoriteCharacters = mutableListOf<Result>()

    fun addFavorite(character: Result) {
        if (!favoriteCharacters.contains(character)) {
            favoriteCharacters.add(character)
        }
    }

    fun getFavorites(): List<Result> {
        return favoriteCharacters
    }
}
