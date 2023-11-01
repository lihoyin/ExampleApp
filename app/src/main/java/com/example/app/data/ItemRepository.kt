package com.example.app.data

import com.example.app.data.local.ExampleDatabase
import com.example.app.data.model.Item
import com.example.app.data.model.Pokemon
import com.example.app.data.remote.PokemonApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val database: ExampleDatabase,
    private val api: PokemonApi
) {
    val allItem: Flow<List<Item>> = database.itemDao().getAll()

    suspend fun insert(item: Item) {
        database.itemDao().insert(item)
    }

    suspend fun update(item: Item) {
        database.itemDao().update(item)
    }

    fun findById(id: Int): Flow<Item?> {
        return database.itemDao().get(id)
    }

    suspend fun getPokemon(name: String): Pokemon {
        val response = api.fetchPokemon(name)

        return Pokemon(
            id = response.id,
            name = response.name,
            image = response.sprites.frontDefault
        )
    }
}
