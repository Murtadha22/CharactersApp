package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.retrofittest.databinding.FragmentHomeBinding
import com.example.retrofittest.models.CharactersResponse
import com.example.retrofittest.models.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllCharacters()
    }

    private fun getAllCharacters() {
        Constants.retrofitService.getCharacter().enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(
                call: Call<CharactersResponse>,
                response: Response<CharactersResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { results ->
                        binding.categoryRv.adapter = CharacterAdapter(results) { character ->
                            navigateToCharacterDescription(character)
                        }
                    }
                } else {
                    Log.i(Constants.TAG, "Response not successful")
                }
            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Log.i(Constants.TAG, "inFailure: ${t.message}")
            }
        })
    }
/////
    private fun navigateToCharacterDescription(character: Result) {
        val action = HomeFragmentDirections.actionHomeFragmentToDiscriptionCharacterFragment(
            name = character.name,
            image = character.image,
            status = character.status,
            gender = character.gender,
            species = character.species,
        )
        findNavController().navigate(action)
    }
}
