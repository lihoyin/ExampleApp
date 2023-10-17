package com.example.app.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.ItemRepository
import com.example.app.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val editState = when (val id = savedStateHandle.get<Int>("id")) {
        null -> flowOf(EditState.Invalid)
        -1 -> flowOf(EditState.Create)
        else -> itemRepository.findById(id).mapLatest {
            it?.let { EditState.Edit(it) } ?: EditState.Invalid
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    fun saveItem(title: String, description: String) {
        viewModelScope.launch {
            when (val state = editState.value) {
                EditState.Create -> itemRepository.insert(
                    Item(
                        title = title,
                        description = description
                    )
                )

                is EditState.Edit -> itemRepository.update(
                    state.item.copy(
                        title = title,
                        description = description
                    )
                )

                else -> return@launch
            }
        }
    }
}

sealed class EditState {
    object Create : EditState()
    data class Edit(val item: Item) : EditState()
    object Invalid : EditState()
}
