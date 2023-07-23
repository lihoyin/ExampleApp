package com.example.app.ui.home

import androidx.lifecycle.ViewModel
import com.example.app.data.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items = _items.asStateFlow()

    init {
        _items.value = listOf(
            Item("Title 1", "Description 1"),
            Item("Title 2", "Description 2"),
            Item("Title 3", "Description 3"),
            Item("Title 4", "Description 4"),
            Item("Title 5", "Description 5"),
            Item("Title 6", "Description 6"),
            Item("Title 7", "Description 7"),
            Item("Title 8", "Description 8"),
            Item("Title 9", "Description 9"),
            Item("Title 10", "Description 10")
        )
    }
}