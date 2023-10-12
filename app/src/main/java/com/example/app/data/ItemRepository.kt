package com.example.app.data

import com.example.app.data.local.AppDatabase
import com.example.app.data.model.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {
    val allItem: Flow<List<Item>> = appDatabase.itemDao().getAll()

    suspend fun insert(item: Item) {
        appDatabase.itemDao().insert(item)
    }

    fun findById(id: Int): Flow<Item>? {
        return appDatabase.itemDao().findById(id)
    }
}