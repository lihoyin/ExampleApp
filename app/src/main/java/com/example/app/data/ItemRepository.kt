package com.example.app.data

import com.example.app.data.local.ExampleDatabase
import com.example.app.data.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val database: ExampleDatabase
) {
    val allItem: Flow<List<Item>> = database.itemDao().getAll()

    suspend fun insert(item: Item) {
        database.itemDao().insert(item)
    }

    suspend fun update(item: Item) {
        database.itemDao().update(item)
    }

    fun getItemFlowById(id: Int) = database.itemDao().getItemAsFlow(id)

    suspend fun getItemById(id: Int) = database.itemDao().getItem(id)
}
