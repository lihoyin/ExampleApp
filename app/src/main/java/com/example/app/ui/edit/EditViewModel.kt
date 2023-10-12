package com.example.app.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.ItemRepository
import com.example.app.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    fun saveItem(title: String, description: String) {
        viewModelScope.launch {
            itemRepository.insert(
                Item(
                    title = title,
                    description = description
                )
            )
        }
    }
}
