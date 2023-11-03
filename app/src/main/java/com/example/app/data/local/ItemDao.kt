package com.example.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItemAsFlow(id: Int): Flow<Item?>

    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun getItem(id: Int): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Query("DELETE FROM item")
    suspend fun deleteAll()
}
