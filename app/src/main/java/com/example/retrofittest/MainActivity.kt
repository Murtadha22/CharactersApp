package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofittest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://rickandmortyapi.com/api/"
    private val TAG: String = "CHECK_RESPONSE"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllCharacters()
    }

    private fun getAllCharacters() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

        api.getCharacter().enqueue(object : Callback<CharactersResponse> {

            override fun onResponse(
                call: Call<CharactersResponse>,
                response: Response<CharactersResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { results ->
                        binding.categoryRv.adapter = CharacterAdapter(results)
                    }
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Log.i(TAG, "inFailure: ${t.message}")
            }

        })


    }
}