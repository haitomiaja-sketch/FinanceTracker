package com.financetracker.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.financetracker.app.data.Transaction
import com.financetracker.app.data.TransactionType
import com.financetracker.app.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.apply {
                titleText.text = transaction.title
                categoryText.text = transaction.category
                amountText.text = buildString {
                    append(if (transaction.type == TransactionType.INCOME) "+ " else "- ")
                    append("Rp. ${transaction.amount}")
                }
                amountText.setTextColor(
                    if (transaction.type == TransactionType.INCOME) 
                        android.graphics.Color.GREEN 
                    else 
                        android.graphics.Color.RED
                )
                dateText.text = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID"))
                    .format(Date(transaction.date))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}
