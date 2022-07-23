package com.mobile.liderstoreagent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.data.models.checkout.PaymentsItem
import com.mobile.liderstoreagent.databinding.ItemTransferMoneyBinding
import com.mobile.liderstoreagent.ui.screens.checkout.viewpager.TransferMoneyFragment

class TransferAdapter(var click: OnClickListener) :
    PagingDataAdapter<PaymentsItem, TransferAdapter.MyHolderView>(object :
        DiffUtil.ItemCallback<PaymentsItem>() {
        override fun areItemsTheSame(oldItem: PaymentsItem, newItem: PaymentsItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PaymentsItem, newItem: PaymentsItem) =
            oldItem == newItem

        override fun getChangePayload(oldItem: PaymentsItem, newItem: PaymentsItem) = false
    }) {
    var sum = 0.0
    var live = MutableLiveData<Double>()
    override fun onBindViewHolder(holder: TransferAdapter.MyHolderView, position: Int) {
        with(holder) {
            var list = getItem(position)

            binding.apply {
                btnEdit.visibility = View.VISIBLE
                tvNumber.text = (position + 1).toString()
                tvsumma.setText(if (list?.payment.isNullOrBlank()) "-" else list?.payment.toString())
                tvPaymentType.setText(if (list?.payment_type.isNullOrBlank()) "-" else list?.payment_type.toString())
                tvStatus.setText(if (list?.status.isNullOrBlank()) "-" else list?.status.toString())
                tvDateTime.setText(
                    if (list?.created_date.isNullOrBlank()) "-" else "${
                        list?.created_date.toString().substringBefore("T")
                    }  ${list?.created_date.toString().substringAfter("T").subSequence(0, 5)}"
                )
                tvComment.setText(if (list?.comments.isNullOrBlank()) "-" else list?.comments.toString())
                tvsubCatergory.visibility = View.GONE
                tvCatergory.visibility = View.GONE

                btnEdit.setOnClickListener {
                    click.updateClick(list)
                }
            }
            /* if (position==0){
                 binding.apply {
                     tvNumber.text =(position+1).toString()
                     tvPaymentType.text ="To'lov turi"
                     tvsumma.text ="To'lov summasi"
                     tvComment.text ="Izoh"
                     tvDateTime.text="Status"
                     tvsubCatergory.visibility=View.GONE
                     tvCatergory.visibility=View.GONE
                     btnEdit.visibility=View.GONE
                 }
             }else{
             }*/
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransferAdapter.MyHolderView {
        var view =
            ItemTransferMoneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolderView(view)
    }

    inner class MyHolderView(var binding: ItemTransferMoneyBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun updateClick(transferModel: PaymentsItem?)
    }

}


/*  interface OnClickListener{
      fun updateClick(transferModel: TransferModel)
  }*/


/*  override fun onCreateViewHolder(
parent: ViewGroup,
viewType: Int
): TransferAdapter.MyHolderView {
var view  =ItemTransferMoneyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
return MyHolderView(view)
}
  override fun onBindViewHolder(holder: TransferAdapter.MyHolderView, position: Int) {
with(holder){

  binding.apply {
      btnEdit.visibility= View.VISIBLE
      tvNumber.text =(position+1).toString()
      tvCatergory.text ="Kategoriya"
      btnEdit.setOnClickListener {
          click.updateClick(list[position])
      }
  }
}
}


  override fun getItemCount(): Int =
*/
