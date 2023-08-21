package com.example.app.ui.home

import androidx.lifecycle.ViewModel
import com.example.app.data.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    itemRepository: ItemRepository
) : ViewModel() {
    val items = itemRepository.allItem
}