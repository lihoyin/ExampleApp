package com.example.app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class PokemonApi @Inject constructor(private val client: HttpClient) {
    suspend fun fetchPokemon(name: String): PokemonResponse = client.get("pokemon/$name").body()
}
