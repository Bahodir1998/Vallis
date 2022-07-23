package com.mobile.liderstoreagent.ui.screens.checkout.viewpager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.CheckOutClientApi
import com.mobile.liderstoreagent.databinding.FragmentOutlayBinding
import com.mobile.liderstoreagent.domain.repositories.HistoryCheckoutRepos
import com.mobile.liderstoreagent.ui.adapters.ListLoadStateAdapter
import com.mobile.liderstoreagent.ui.adapters.OutlayAdapter
import com.mobile.liderstoreagent.ui.viewmodels.CheckOutHistoryFactoryModel
import com.mobile.liderstoreagent.ui.viewmodels.CheckOutHistoryViewModel
import com.mobile.liderstoreagent.ui.viewmodels.CheckOutViewModel
import kotlinx.android.synthetic.main.fragment_transfer_money.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.YearMonth


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OutlayFragment : Fragment(R.layout.fragment_outlay) {
    private val checkOutViewModel: CheckOutViewModel by viewModels()

    private val pagerViewModel by lazy { initViewModel() }
    private val adapter by lazy { OutlayAdapter() }
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentOutlayBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOutlayBinding.inflate(layoutInflater)

           return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers(param1!!,param2!!)
        initRecyclerView()

    }


    private fun setObservers(startDate:String,endDate:String) = with(pagerViewModel) {
        getExpenseHistory(startDate,endDate).observe(viewLifecycleOwner) {it->
            viewLifecycleOwner.lifecycleScope.launch {
                Log.e("expanseDate",it.toString() )
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->

                /* binding.clientProductsProgressBar.isVisible =
                     loadStates.refresh is LoadState.Loading*/
//                binding.srlRefresh.isRefreshing = loadStates.refresh is LoadState.Loading


                loadStates.refresh.let {
                    if (it is LoadState.Error) {
                        Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    private fun initRecyclerView()  {
        recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            ListLoadStateAdapter { adapter.retry() },
            ListLoadStateAdapter { adapter.retry() }
        )

    }

    private fun initViewModel() = ViewModelProvider(
        this, CheckOutHistoryFactoryModel(
            requireContext(),
            HistoryCheckoutRepos(ApiClient.retrofit.create(CheckOutClientApi::class.java))
        )
    ).get(CheckOutHistoryViewModel::class.java)



    companion object {
        fun newInstance(param1: String, param2: String) =
            OutlayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}