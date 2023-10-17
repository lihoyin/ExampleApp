package com.example.app.ui.edit

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.app.data.ItemRepository
import com.example.app.data.model.Item
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class EditViewModelTest {
    private val savedStateHandle = mockk<SavedStateHandle>()
    private val itemRepository = mockk<ItemRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN valid item id WHEN init THEN state is edit with item retrieved`() = runTest {
        val expectedItem = Item(
            id = 1,
            title = "title",
            description = "description"
        )
        every { savedStateHandle.get<Int>("id") } returns 1
        every { itemRepository.findById(1) } returns flowOf(expectedItem)

        val editViewModel = EditViewModel(
            savedStateHandle = savedStateHandle,
            itemRepository = itemRepository
        )

        editViewModel.editState.test {
            awaitItem()
            assertEquals(expectedItem, (awaitItem() as EditState.Edit).item)
        }
    }

    @Test
    fun `GIVEN no item id WHEN init THEN state is create`() = runTest {
        every { savedStateHandle.get<Int>("id") } returns -1

        val editViewModel = EditViewModel(
            savedStateHandle = savedStateHandle,
            itemRepository = itemRepository
        )

        editViewModel.editState.test {
            awaitItem()
            assertEquals(EditState.Create, awaitItem())
        }
    }

    @Test
    fun `GIVEN invalid item id WHEN init THEN state is invalid`() = runTest {
        every { savedStateHandle.get<Int>("id") } returns 99
        every { itemRepository.findById(99) } returns flowOf(null)

        val editViewModel = EditViewModel(
            savedStateHandle = savedStateHandle,
            itemRepository = itemRepository
        )

        editViewModel.editState.test {
            awaitItem()
            assertEquals(EditState.Invalid, awaitItem())
        }
    }
}
