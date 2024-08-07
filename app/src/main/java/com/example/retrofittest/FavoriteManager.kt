package com.example.retrofittest

import com.example.retrofittest.models.Result

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
