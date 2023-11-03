package com.example.app.data

import com.example.app.data.model.Target
import com.example.app.data.remote.PokemonApi
import javax.inject.Inject

class TargetRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend fun getTarget(name: String): Target {
        val response = pokemonApi.fetchPokemon(name)

        return Target(
            id = response.id,
            name = response.name,
            image = response.sprites.frontDefault
        )
    }
}
