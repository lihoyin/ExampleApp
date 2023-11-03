package com.example.app.ui.edit

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.app.data.ItemRepository
import com.example.app.data.TargetRepository
import com.example.app.data.model.Item
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val targetRepository = mockk<TargetRepository>()

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
        coEvery { itemRepository.getItemById(1) } returns expectedItem

        val editViewModel = EditViewModel(
            savedStateHandle = savedStateHandle,
            itemRepository = itemRepository,
            targetRepository = targetRepository
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
            itemRepository = itemRepository,
            targetRepository = targetRepository
        )

        editViewModel.editState.test {
            awaitItem()
            assertEquals(EditState.Create, awaitItem())
        }
    }

    @Test
    fun `GIVEN invalid item id WHEN init THEN state is invalid`() = runTest {
        every { savedStateHandle.get<Int>("id") } returns 99
        coEvery { itemRepository.getItemById(99) } returns null

        val editViewModel = EditViewModel(
            savedStateHandle = savedStateHandle,
            itemRepository = itemRepository,
            targetRepository = targetRepository
        )

        editViewModel.editState.test {
            awaitItem()
            assertEquals(EditState.Invalid, awaitItem())
        }
    }
}
