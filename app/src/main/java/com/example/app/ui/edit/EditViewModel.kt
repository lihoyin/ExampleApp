package com.example.app.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.ItemRepository
import com.example.app.data.TargetRepository
import com.example.app.data.model.Item
import com.example.app.data.model.Target
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository,
    private val targetRepository: TargetRepository
) : ViewModel() {
    private val _editState = MutableStateFlow<EditState>(EditState.Loading)
    val editState = _editState.asStateFlow()

    private val _selectedTarget = MutableStateFlow<Target?>(null)
    val selectedTarget = _selectedTarget.asStateFlow()

    init {
        viewModelScope.launch {
            val state = when (val id = savedStateHandle.get<Int>("id")) {
                null -> EditState.Invalid
                -1 -> EditState.Create
                else -> itemRepository.getItemById(id)?.let {
                    EditState.Edit(it)
                } ?: EditState.Invalid
            }

            state.takeIf { (it as? EditState.Edit)?.item?.targetId != null }?.let {
                selectTarget(it.toString())
            }

            _editState.value = state
        }
    }

    fun selectTarget(id: String) {
        viewModelScope.launch {
            _selectedTarget.value = targetRepository.getTarget(id)
        }
    }

    fun clearTarget() {
        viewModelScope.launch {
            _selectedTarget.value = null
        }
    }

    fun saveItem(title: String, description: String) {
        viewModelScope.launch {
            when (val state = editState.value) {
                EditState.Create -> itemRepository.insert(
                    Item(
                        title = title,
                        description = description,
                        targetId = selectedTarget.value?.id
                    )
                )

                is EditState.Edit -> itemRepository.update(
                    state.item.copy(
                        title = title,
                        description = description,
                        targetId = selectedTarget.value?.id
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
    object Loading : EditState()
}
