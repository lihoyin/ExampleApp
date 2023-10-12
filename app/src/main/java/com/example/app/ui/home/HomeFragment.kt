package com.example.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.app.MainNavDirections
import com.example.app.data.model.Item
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.ui.base.BaseFragment
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ItemListListener {

    private val viewModel: HomeViewModel by viewModels()
    private val itemListAdapter = ItemListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItemList.adapter = itemListAdapter
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(MainNavDirections.actionToEditFragment())
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.items.collect {
                    itemListAdapter.submitList(it)
                }
            }
        }
    }

    override fun onItemPressed(item: Item) {
        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SELECT_ITEM,
            bundleOf(FirebaseAnalytics.Param.ITEM_ID to item.id)
        )

        findNavController().navigate(MainNavDirections.actionToDetailFragment(item.id))
    }
}
