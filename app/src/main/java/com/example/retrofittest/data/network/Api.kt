package com.example.retrofittest.data.network

import com.example.retrofittest.models.CharactersResponse
import com.example.retrofittest.models.chosecharacter.ChoseCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("character")
    suspend fun getCharacter(): Response<CharactersResponse>

    @GET("character/{characterId}")
    suspend fun getCharacter2(@Path("characterId}") id: Int): Response<ChoseCharacter>
}
