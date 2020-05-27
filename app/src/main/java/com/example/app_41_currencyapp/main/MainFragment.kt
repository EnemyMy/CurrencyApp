package com.example.app_41_currencyapp.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app_41_currencyapp.R
import com.example.app_41_currencyapp.application.CurrencyApplication
import com.example.app_41_currencyapp.data.ChosenCurrency
import com.example.app_41_currencyapp.databinding.FragmentMainBinding
import com.example.app_41_currencyapp.util.SnackbarEvent
import com.example.app_41_currencyapp.util.showSnackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class MainFragment: MvpAppCompatFragment(), MainView, SwipeRefreshLayout.OnRefreshListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerAdapter: MainFragmentAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as CurrencyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.main_fragment_title)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Current presenter", "$presenter")
        initRecycler()
        binding.mainFragmentSwipeRefresh.setOnRefreshListener(this)
    }

    private fun initRecycler() {
        recyclerAdapter = MainFragmentAdapter()
        binding.mainFragmentRecycler.apply {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
                RecyclerItemTouchHelper(this@MainFragment, 0, ItemTouchHelper.LEFT)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }
        presenter.onQueryCurrency(getString(R.string.api_key), recyclerAdapter.currentList)
    }

    override fun showProgress() {
        if (!binding.mainFragmentSwipeRefresh.isRefreshing)
            binding.mainFragmentSwipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        if (binding.mainFragmentSwipeRefresh.isRefreshing)
            binding.mainFragmentSwipeRefresh.isRefreshing = false
    }

    override fun onRefresh() {
        presenter.onQueryCurrency(getString(R.string.api_key), recyclerAdapter.currentList)
    }

    override fun showList() {
        binding.mainFragmentRecycler.visibility = View.VISIBLE
    }

    override fun hideList() {
        binding.mainFragmentRecycler.visibility = View.GONE
    }

    override fun submitNewList(list: List<ChosenCurrency>) {
        recyclerAdapter.submitList(list)
    }

    override fun showChooseChartsAnim() {
        binding.mainFragmentChooseChartsAnimation.visibility = View.VISIBLE
        binding.mainFragmentChooseChartsText.visibility = View.VISIBLE
    }

    override fun hideChooseChartsAnim() {
        binding.mainFragmentChooseChartsAnimation.visibility = View.GONE
        binding.mainFragmentChooseChartsText.visibility = View.GONE
    }

    override fun showNetworkErrorAnim() {
        binding.mainFragmentNetworkErrorAnimation.visibility = View.VISIBLE
        binding.mainFragmentNetworkErrorText.visibility = View.VISIBLE
    }

    override fun hideNetworkErrorAnim() {
        binding.mainFragmentNetworkErrorAnimation.visibility = View.GONE
        binding.mainFragmentNetworkErrorText.visibility = View.GONE
    }

    override fun showSnackbar(event: SnackbarEvent) {
        binding.root.showSnackbar(event)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        val list = recyclerAdapter.currentList
        presenter.onDeleteChosenCurrency(position, list)
    }
}