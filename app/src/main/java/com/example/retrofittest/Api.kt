package com.example.retrofittest

import com.example.retrofittest.models.getallcharacters.CharactersResponse
import com.example.retrofittest.models.getallcharacters.chosecharacter.ChoseCharacter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("character")
    fun getCharacter(): Call<CharactersResponse>
    @GET("character/{characterId}")
    fun getCharacter2(@Path("characterId") id: Int): Call<ChoseCharacter>

}
