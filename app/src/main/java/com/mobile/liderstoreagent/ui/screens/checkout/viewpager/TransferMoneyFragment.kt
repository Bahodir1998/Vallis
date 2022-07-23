package com.mobile.liderstoreagent.ui.screens.checkout.viewpager

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.checkout.PaymentsItem
import com.mobile.liderstoreagent.data.source.remote.retrofit.ApiClient
import com.mobile.liderstoreagent.data.source.remote.retrofit.CheckOutClientApi
import com.mobile.liderstoreagent.databinding.FragmentTransferMoneyBinding
import com.mobile.liderstoreagent.domain.repositories.HistoryCheckoutRepos
import com.mobile.liderstoreagent.ui.MainActivity
import com.mobile.liderstoreagent.ui.adapters.ListLoadStateAdapter
import com.mobile.liderstoreagent.ui.adapters.TransferAdapter
import com.mobile.liderstoreagent.ui.viewmodels.*
import kotlinx.android.synthetic.main.fragment_transfer_money.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TransferMoneyFragment : Fragment(),TransferAdapter.OnClickListener {
    private val checkOutViewModel: CheckOutViewModel by viewModels()
    private val pagerViewModel by lazy { initViewModel() }
    private val adapter by lazy { TransferAdapter(this) }
    private var param1: String? = null
    private var param2: String? = null
    var sum = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentTransferMoneyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransferMoneyBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers(param1!!,param2!!)
        initRecyclerView()

       /* recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =LinearLayoutManager(requireContext())
        recyclerview.adapter =TransferAdapter(this)
*/
    }
    private fun setObservers(startDate:String,endDate:String) = with(pagerViewModel) {
        getTransferHistory(startDate,endDate).observe(viewLifecycleOwner) {it->
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
                 ///    Log.e( "setObservers: ", )
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
        binding.tvsubCatergory.visibility = View.GONE
        binding.tvCatergory.visibility = View.GONE
    }
   /* private fun setObservers() = with(pagerViewModel) {
        get("").observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                historyAdapter.submitData(it)
            }
        }*/
    private fun initViewModel() = ViewModelProvider(
        this, CheckOutHistoryFactoryModel(
            requireContext(),
            HistoryCheckoutRepos(ApiClient.retrofit.create(CheckOutClientApi::class.java))
        )
    ).get(CheckOutHistoryViewModel::class.java)
    companion object {
        var transfer:PaymentsItem= PaymentsItem()
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransferMoneyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

  /*  private fun historySetUp() {
        checkOutViewModel.errorHistoryLiveData.observe(viewLifecycleOwner,
            errorHistorysObserver)
        historyViewModel.progressHistoryLiveData.observe(viewLifecycleOwner, progressObserver)

        historyViewModel.connectionErrorHistoryLiveData.observe(
            viewLifecycleOwner,
            connectionErrorObserver
        )
        historyViewModel.successHistoryLiveData.observe(
            viewLifecycleOwner,
            successOwnCategoriesObserver
        )
    }*/


    override fun updateClick(transferModel: PaymentsItem?) {
        MainActivity.UPDATE= 1
        transfer= transferModel!!
        val bundle = Bundle()
        transferModel.id?.let { bundle.putInt("id", it) }
        findNavController().navigate(R.id.inputTransferMoneyFragment,bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}