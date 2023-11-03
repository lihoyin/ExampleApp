package com.example.app.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.ItemRepository
import com.example.app.data.TargetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    itemRepository: ItemRepository,
    targetRepository: TargetRepository
) : ViewModel() {
    val item = itemRepository.getItemFlowById(savedStateHandle.get<Int>("id")!!).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val target = item.mapLatest { item ->
        item?.targetId?.let { targetRepository.getTarget(it.toString()) }
    }
}
