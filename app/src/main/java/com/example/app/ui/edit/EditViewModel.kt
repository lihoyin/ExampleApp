package com.example.app.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.ItemRepository
import com.example.app.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository
) : ViewModel() {
    val item = when (val id = savedStateHandle.get<Int>("id")!!) {
        -1 -> flowOf(Item())
        else -> itemRepository.findById(id)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        Item()
    )

    fun saveItem(title: String, description: String) {
        viewModelScope.launch {
            itemRepository.insert(
                item.value!!.copy(
                    title = title,
                    description = description
                )
            )
        }
    }
}
