package com.mobile.liderstoreagent.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.data.models.categorymodel.CategoryData
import com.mobile.liderstoreagent.data.models.checkout.ExpensesItem
import com.mobile.liderstoreagent.data.models.checkout.PaymentsItem
import com.mobile.liderstoreagent.data.models.checkoutmodeel.TransferModel
import com.mobile.liderstoreagent.databinding.ItemTransferMoneyBinding

class OutlayAdapter() :
    PagingDataAdapter<ExpensesItem, OutlayAdapter.MyHolderView>(object :
        DiffUtil.ItemCallback<ExpensesItem>() {
        override fun areItemsTheSame(oldItem: ExpensesItem, newItem: ExpensesItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ExpensesItem, newItem: ExpensesItem) =
            oldItem == newItem

        override fun getChangePayload(oldItem: ExpensesItem, newItem: ExpensesItem) = false

    }) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OutlayAdapter.MyHolderView {
        var view =
            ItemTransferMoneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolderView(view)
    }

    override fun onBindViewHolder(holder: OutlayAdapter.MyHolderView, position: Int) {
        var list = getItem(position)!!
        with(holder) {
            binding.apply {
                tvNumber.text = (position + 1).toString()
                tvCatergory.setText(if (list?.category?.name.isNullOrBlank()) "-" else list.category!!.name)
                tvsubCatergory.setText(if (list?.subcategory?.name.isNullOrBlank()) "-" else list.subcategory!!.name)
                tvsumma.setText(if (list?.money.isNullOrBlank()) "-" else list.money.toString())
                tvPaymentType.setText(if (list?.payment_type.isNullOrBlank()) "-" else list.payment_type)
                tvComment.setText(if (list?.comment.isNullOrBlank()) "-" else list.comment)
                tvDateTime.setText(if (list?.created_date.isNullOrBlank()) "-" else "${list.created_date.toString().substringBefore("T")}  ${list.created_date.toString().substringAfter("T").subSequence(0,5)}")
            }
        }
    }

    class MyHolderView(var binding: ItemTransferMoneyBinding) :
        RecyclerView.ViewHolder(binding.root)
}