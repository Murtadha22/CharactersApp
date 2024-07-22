package com.example.retrofittest

import retrofit2.Call
import retrofit2.http.GET
import javax.security.auth.callback.Callback

interface Api {
    @GET("character")
    fun getCharacter(): Call<CharactersResponse>
}
