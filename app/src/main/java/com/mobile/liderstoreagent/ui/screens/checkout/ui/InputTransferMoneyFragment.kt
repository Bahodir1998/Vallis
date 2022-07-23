package com.mobile.liderstoreagent.ui.screens.checkout.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.checkout.PaymentsItem
import com.mobile.liderstoreagent.data.models.checkout.TransferRequestModel
import com.mobile.liderstoreagent.data.models.checkout.UpdateRequestModel
import com.mobile.liderstoreagent.data.models.checkoutmodeel.TransferModel
import com.mobile.liderstoreagent.databinding.FragmentInputOutlayBinding
import com.mobile.liderstoreagent.databinding.FragmentInputTransferMoneyBinding
import com.mobile.liderstoreagent.databinding.FragmentTransferMoneyBinding
import com.mobile.liderstoreagent.databinding.ItemNotFundDialogBinding
import com.mobile.liderstoreagent.ui.MainActivity.Companion.UPDATE
import com.mobile.liderstoreagent.ui.screens.checkout.viewpager.TransferMoneyFragment
import com.mobile.liderstoreagent.ui.viewmodels.CheckOutViewModel
import com.mobile.liderstoreagent.utils.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class InputTransferMoneyFragment : Fragment(R.layout.fragment_input_transfer_money),CoroutineScope {
    var _binding: FragmentInputTransferMoneyBinding?=null
    val binding  get() =_binding!!
    private val checkOutViewModel: CheckOutViewModel by viewModels()
    var transfer:PaymentsItem= PaymentsItem()
    var paymentType =""
    var  paymentTypeKey =ArrayList<String>()
    var paymentTypeList =ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transfer =TransferMoneyFragment.transfer

        paymentTypeKey.add("dollar")
        paymentTypeKey.add("sum")
        paymentTypeKey.add("karta")
        paymentTypeKey.add("transfer")

        paymentTypeList.add("Dollar")
        paymentTypeList.add("So'm")
        paymentTypeList.add("Karta")
        paymentTypeList.add("Bank")


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentInputTransferMoneyBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backToHomeAddClient.setOnClickListener {
            findNavController().popBackStack()
        }
        checkOutViewModel.getExpenseCategory()
        binding.apply {

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, paymentTypeList)
            etPaymentType.setAdapter(adapter)
            etPaymentType.setOnItemClickListener { adapterView, view, pos, l ->
                paymentType =paymentTypeKey[pos]
                // Log.e( "position",categoryId.toString() )
                //    checkOutViewModel.getExpenseSubCategory(categoryId)

            }

            btnAdd.setOnClickListener {

                if (UPDATE==1){
                    var data =UpdateRequestModel(paymentType,SharedPref.user?.toInt(),etComment.text.toString(),etSumma.text.toString())
                    val id = arguments?.getInt("id")
                    id?.let { it1 -> checkOutViewModel.updateTransferRequest(it1, UpdateRequestModel(paymentType,SharedPref.user?.toInt(),etComment.text.toString(),etSumma.text.toString())) }
                }else{
                    checkOutViewModel.setTransferRequest(TransferRequestModel(paymentType,SharedPref.user?.toInt(),etComment.text.toString(),etSumma.text.toString()))
                }

                Handler().postDelayed({if (checkOutViewModel.successTransferRequestLiveData.hasObservers()||checkOutViewModel.successUpdateTransferLiveData.hasObservers()){
                    val dialog = Dialog(requireContext())
                    val dialogBinding = ItemNotFundDialogBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.item_not_fund_dialog,binding.root,false))
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialogBinding.ok.setOnClickListener {
                        dialog.cancel()
                    }
                    dialog.setContentView(dialogBinding.root)
                    dialog.show()
                } },500)
            }
            checkOutViewModel.successUpdateTransferLiveData.observe(viewLifecycleOwner){
                findNavController().navigateUp()
            }
            checkOutViewModel.successTransferRequestLiveData.observe(viewLifecycleOwner){
               findNavController().navigateUp()
            }
        }
        var index =0
        for (i in paymentTypeKey.indices){
            var paymentKey =paymentTypeKey.find { it==transfer.payment_type }
            if (paymentKey!=null){
                index =i
                break
            }
        }

        binding.etPaymentType.setSelection(index)
        binding.etSumma.setText(if(transfer.payment==null) "" else transfer.payment)
        Log.e( "onCreate: ",transfer.toString())
        arguments?.let {
        }

      //  requireActivity().onBackPressed()


    }
    override fun onDestroy() {
        super.onDestroy()
      _binding =null
    }
    companion object {

        fun newInstance(param1: String, param2: String) =
            InputTransferMoneyFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()+Dispatchers.IO
}