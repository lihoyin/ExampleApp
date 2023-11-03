package com.example.app.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.app.R
import com.example.app.databinding.FragmentEditBinding
import com.example.app.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFragment : BaseFragment<FragmentEditBinding>(FragmentEditBinding::inflate), MenuProvider {
    private val viewModel: EditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSelect.setOnClickListener {
            val editText = EditText(requireContext())
            AlertDialog.Builder(requireContext())
                .setView(editText)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    viewModel.selectTarget(editText.text.toString())
                }
                .setNegativeButton(R.string.cancel, null)
                .create().show()
        }

        binding.btnDelete.setOnClickListener { viewModel.clearTarget() }

        lifecycleScope.launch {
            viewModel.editState.filterNotNull().collect {
                when (it) {
                    EditState.Loading -> {
                        // do nothing
                    }

                    EditState.Create -> {
                        // do nothing
                    }

                    is EditState.Edit -> {
                        binding.etTitle.setText(it.item.title)
                        binding.etDescription.setText(it.item.description)
                    }

                    EditState.Invalid -> findNavController().popBackStack()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.selectedTarget.collect {
                    binding.tvTargetName.text = it?.name ?: resources.getText(R.string.not_selected)
                    binding.ivTargetImage.load(it?.image)
                    binding.btnDelete.isVisible = it != null
                }
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_save -> viewModel.saveItem(
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString()
            )
        }

        findNavController().popBackStack()
        return false
    }
}
