package com.mobile.liderstoreagent.ui.screens

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.launch
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.databinding.FragmentCheckoutBinding
import com.mobile.liderstoreagent.ui.MainActivity.Companion.UPDATE
import com.mobile.liderstoreagent.ui.adapters.CheckOutViewAdapter
import com.mobile.liderstoreagent.ui.adapters.HomeHistoryViewAdapter
import com.mobile.liderstoreagent.ui.viewmodels.CheckOutViewModel
import com.mobile.liderstoreagent.ui.viewmodels.ProductsPageViewModel
import com.mobile.liderstoreagent.utils.NetworkHelper
import com.mobile.liderstoreagent.utils.SharedPref
import kotlinx.android.synthetic.main.clients_fragment.*
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.product_sold_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*
import kotlin.coroutines.CoroutineContext

class CheckoutFragment : Fragment(R.layout.fragment_checkout),CoroutineScope{
    var _binding:FragmentCheckoutBinding?=null
    val binding  get() =_binding!!

    private val checkOutViewModel: CheckOutViewModel by viewModels()
    lateinit var checkOutTabAdapter:CheckOutViewAdapter

    private lateinit var dateSetListenerFrom: DatePickerDialog.OnDateSetListener
    private lateinit var dateSetListenerUntil: DatePickerDialog.OnDateSetListener
    var cal: Calendar = Calendar.getInstance()
    private var dateStart: String = ""
    private var dateEnd: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     _binding = FragmentCheckoutBinding.inflate(inflater,container,false)
        binding.viewPager.isUserInputEnabled = false
        loadUi()
        return binding.root
    }

    private fun loadUi() {
        GlobalScope.launch {
            if (NetworkHelper(requireContext()).isNetworkConnect()){
                SharedPref.getInstanceDis(requireContext())
                val agent = SharedPref.user?.let { ApiClient.apiService.getkassa(it) }
                binding.dollar.setText(agent?.data?.total_dollar)
                binding.som.setText(agent?.data?.total_sum)
                binding.bank.setText(agent?.data?.total_transfer)
                binding.plastic.setText(agent?.data?.total_karta)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh.setOnRefreshListener {
            loadUi()
            loadViewPager()
            Handler().postDelayed(Runnable {
                refresh.isRefreshing = false
            }, 1000)
        }

        loadViewPager()
        datePick()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadViewPager(){
        binding.apply{
            val now = LocalDateTime.now().toString().subSequence(0,8)
            val yearMonthObject: YearMonth = YearMonth.of(now.subSequence(0,4).toString().toInt(),now.subSequence(5,7).toString().toInt())
            val daysInMonth: Int = yearMonthObject.lengthOfMonth()
            checkOutTabAdapter =CheckOutViewAdapter(requireActivity(),"${now}01","${now}$daysInMonth")

            viewPager.adapter = checkOutTabAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when(position) {
                    0->{
                        tab.text = "Pul topshirish"
                    }
                    1 -> {
                        tab.text = "Chiqim qilish "
                    }
                }

            }.attach()

            backToHomeSoldProducts.setOnClickListener {
                findNavController().navigateUp()
            }

            checkOutViewModel.getExpenseCategory()
            btnOutlay.setOnClickListener {it0->
                findNavController().navigate(R.id.action_checkoutFragment_to_inputOutlayFragment)
            }
            btnTransferMoney.setOnClickListener {it0->
                UPDATE =0
                findNavController().navigate(R.id.action_checkoutFragment_to_inputTransferMoneyFragment)
            }
        }
    }
    private fun datePick() {
        binding.apply {
            txtUntil.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    dateSetListenerUntil,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            txtFrom.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    dateSetListenerFrom,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
        dateSetListenerFrom =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewFrom()
            }
        dateSetListenerUntil =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewUntil()
            }
    }
    private fun updateDateInViewFrom() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.txtFrom.text = sdf.format(cal.time)
        dateStart = binding.txtFrom.text.toString()
        if (dateEnd != "") {
            getAcceptHistory(SharedPref.user.toString(), dateEnd, dateStart)
            updateViewPager(dateEnd,dateStart)
        } else {
            Toast.makeText(
                requireContext(),
                "Qaysi sanadan ekanligini kiriting",
                Toast.LENGTH_SHORT
            ).show()
        }
        Toast.makeText(
            requireContext(),
            binding.txtFrom.text.toString() + "\n" + dateEnd,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateDateInViewUntil() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.txtUntil.text = sdf.format(cal.time)
        dateEnd = binding.txtUntil.text.toString()
        if (dateStart != "") {
            getAcceptHistory(SharedPref.user.toString(), dateEnd, dateStart)
            updateViewPager(dateEnd,dateStart)
        } else {
            Toast.makeText(
                requireContext(),
                "Qaysi sanagacha ekanligini kiriting",
                Toast.LENGTH_SHORT
            ).show()
        }
        Toast.makeText(requireContext(), dateStart + "\n" + dateEnd, Toast.LENGTH_SHORT).show()
    }
    private fun updateViewPager(start:String,end:String){
        checkOutTabAdapter =CheckOutViewAdapter(requireActivity(),start,end)

        binding.viewPager.adapter = checkOutTabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0->{
                    tab.text = "Pul topshirish"
                }
                1 -> {
                    tab.text = "Chiqim qilish "
                }
            }

        }.attach()
    }
    private fun getAcceptHistory(s: String, dateEnd: String, dateStart: String) {
      /*  viewModel.getProfit(s, dateEnd, dateStart)
        launch(Dispatchers.Main) {
            viewModel.order.collect {
             binding.jamiOmbor.text = it?.total_shop_total_warehouse_shop.toString()
                binding.jamiQarz.text = it?.client_total_debt.toString()
                binding.jamiQoldiq.text = it?.total_warehouse_product_price.toString()
                binding.jamiSavdo.text = it?.total_shop.toString()
            }
        }*/
    }
    companion object {
       @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckoutFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override val coroutineContext: CoroutineContext
        get() = Job()+Dispatchers.IO
    /* override val coroutineContext: CoroutineContext
         get() =  Job()+Dispatchers.IO*/
}