package com.financetracker.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.financetracker.app.R
import com.financetracker.app.data.TransactionType
import com.financetracker.app.databinding.DialogAddTransactionBinding
import com.financetracker.app.viewmodel.TransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTransactionFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogAddTransactionBinding
    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        setupCategorySpinner()
        setupTypeSpinner()

        binding.saveButton.setOnClickListener {
            saveTransaction()
        }
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf("Food", "Transport", "Entertainment", "Shopping", "Utilities", "Other")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }

    private fun setupTypeSpinner() {
        val types = arrayOf("Income", "Expense")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = adapter
    }

    private fun saveTransaction() {
        val title = binding.titleInput.text.toString().trim()
        val amountStr = binding.amountInput.text.toString().trim()
        val category = binding.categorySpinner.selectedItem.toString()
        val type = if (binding.typeSpinner.selectedItemPosition == 0) TransactionType.INCOME else TransactionType.EXPENSE
        val notes = binding.notesInput.text.toString().trim()

        if (title.isEmpty() || amountStr.isEmpty()) {
            return
        }

        val amount = amountStr.toDoubleOrNull() ?: return

        viewModel.addTransaction(title, amount, category, type, notes)
        dismiss()
    }
}
