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
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.checkout.ExpanseRequestModel
import com.mobile.liderstoreagent.databinding.FragmentCheckoutBinding
import com.mobile.liderstoreagent.databinding.FragmentInputOutlayBinding
import com.mobile.liderstoreagent.databinding.ItemNotFundDialogBinding
import com.mobile.liderstoreagent.ui.MainActivity
import com.mobile.liderstoreagent.ui.viewmodels.CheckOutViewModel
import com.mobile.liderstoreagent.utils.SharedPref

class InputOutlayFragment : Fragment(R.layout.fragment_input_outlay) {
    var _binding: FragmentInputOutlayBinding?=null
    val binding  get() =_binding!!
     var categoryId=0
     var subCategoryId=0
    var paymentType =""
    var  paymentTypeKey =ArrayList<String>()
    var paymentTypeList =ArrayList<String>()
    private val checkOutViewModel: CheckOutViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentTypeKey.add("dollar")
        paymentTypeKey.add("sum")
        paymentTypeKey.add("karta")
        paymentTypeKey.add("transfer")

        paymentTypeList.add("Dollar")
        paymentTypeList.add("So'm")
        paymentTypeList.add("Karta")
        paymentTypeList.add("Bank")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentInputOutlayBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
        backToHomeAddClient.setOnClickListener {
            Log.e( "onViewCreated: ","backpressed")
            findNavController().popBackStack()
        }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, paymentTypeList)
            etPaymentType.setAdapter(adapter)
            etPaymentType.setOnItemClickListener { adapterView, view, pos, l ->
                 paymentType =paymentTypeKey[pos]
                // Log.e( "position",categoryId.toString() )
            //    checkOutViewModel.getExpenseSubCategory(categoryId)

            }



      checkOutViewModel.getExpenseCategory()
        checkOutViewModel.successExpenseLiveData.observe(viewLifecycleOwner) {
            Log.e( "onViewCreated: ",it.toString() )
             var list =ArrayList<String>()
             it.results.forEach { it0->
               list.add(it0.name)
             }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
            etCategory.setAdapter(adapter)
            etCategory.setOnItemClickListener { adapterView, view, pos, l ->
                categoryId =it.results[pos].id
               // Log.e( "position",categoryId.toString() )
                checkOutViewModel.getExpenseSubCategory(categoryId)

            }
        }
            checkOutViewModel.progressWarehouseLiveData.observe(viewLifecycleOwner){
              //  (requireActivity() as MainActivity).
            }

            checkOutViewModel.successSubCategoryExpenseLiveData.observe(viewLifecycleOwner){
                var list =ArrayList<String>()
                it.data?.forEach { it0->
                    list.add(it0?.name.toString())
                }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
                etSubCategory.setAdapter(adapter)
                etSubCategory.setOnItemClickListener { adapterView, view, pos, l ->
                    subCategoryId = it.data!![pos]?.id!!
                    Log.e("position",subCategoryId.toString())
                }
            }
     }

        binding.btnAdd.setOnClickListener {
            if(categoryId==0||subCategoryId==0||binding.etSumma.text.toString().isEmpty()||paymentType.isEmpty()){
                Toast.makeText(requireContext(), "To'ldiring", Toast.LENGTH_SHORT).show()
              return@setOnClickListener
            }else{

                checkOutViewModel.setExpenseRequest(ExpanseRequestModel(paymentType,SharedPref.user?.toInt(),binding.etSumma.text.toString(),binding.etComment.text.toString(),categoryId.toString(),subCategoryId.toString()))

                Handler().postDelayed({if (checkOutViewModel.successExpenseRequestLiveData.hasObservers()||checkOutViewModel.connectionErrorExpenseRequestLiveData.hasObservers()){
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
            checkOutViewModel.successExpenseRequestLiveData.observe(viewLifecycleOwner){
                findNavController().navigateUp()
            }

            checkOutViewModel.connectionErrorExpenseRequestLiveData.observe(viewLifecycleOwner) {it->

            }

        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InputOutlayFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}