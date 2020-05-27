package com.example.app_41_currencyapp.addcurrency

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import com.example.app_41_currencyapp.R
import com.example.app_41_currencyapp.application.CurrencyApplication
import com.example.app_41_currencyapp.databinding.FragmentAddCurrencyBinding
import com.example.app_41_currencyapp.main.MainActivity
import com.example.app_41_currencyapp.util.SnackbarEvent
import com.example.app_41_currencyapp.util.showSnackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class AddCurrencyFragment: MvpAppCompatFragment(), SearchView.OnQueryTextListener, AddCurrencyView, View.OnClickListener {

    @Inject
    lateinit var presenterProvider: Provider<AddCurrencyPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }
    private lateinit var binding: FragmentAddCurrencyBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as CurrencyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.add_currency_fragment_title)
        setHasOptionsMenu(true)
        binding = FragmentAddCurrencyBinding.inflate(inflater, container, false)
        binding.fragmentAddCurrencyButton.setOnClickListener(this)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_currency_fragment_menu_items, menu)
        val myActionMenuItem = menu.findItem(R.id.add_currency_fragment_menu_item_search_currency)
        (myActionMenuItem.actionView as SearchView).let {
            it.setOnQueryTextListener(this)
            it.setIconifiedByDefault(true)
            it.queryHint = "Enter searched currency"
            it.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        presenter.onQueryCurrency(getString(R.string.api_key), query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun showProgress() {
        binding.fragmentAddCurrencyProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.fragmentAddCurrencyProgress.visibility = View.GONE
    }

    override fun showResult(title: String, value: String) {
        binding.fragmentAddCurrencyCard.visibility = View.VISIBLE
        binding.fragmentAddCurrencyTitle.text = title
        binding.fragmentAddCurrencyValue.text = value
    }

    override fun hideResult() {
        binding.fragmentAddCurrencyCard.visibility = View.GONE
    }

    override fun hideAddCurrencyAnim() {
        binding.animationViewSelectCurrency.visibility = View.GONE
        binding.fragmentAddCurrencySelectCurrencyText.visibility = View.GONE
    }

    override fun showNoResult() {
        binding.fragmentAddCurrencyNoResultImage.visibility = View.VISIBLE
        binding.fragmentAddCurrencyNoResultText.visibility = View.VISIBLE
    }

    override fun hideNoResult() {
        binding.fragmentAddCurrencyNoResultImage.visibility = View.GONE
        binding.fragmentAddCurrencyNoResultText.visibility = View.GONE
    }

    override fun showNetworkErrorAnim() {
        binding.animationViewNetworkError.visibility = View.VISIBLE
        binding.fragmentAddCurrencyNetworkErrorText.visibility = View.VISIBLE
    }

    override fun hideNetworkErrorAnim() {
        binding.animationViewNetworkError.visibility = View.GONE
        binding.fragmentAddCurrencyNetworkErrorText.visibility = View.GONE
    }

    override fun showSnackbar(event: SnackbarEvent) {
        binding.root.showSnackbar(event)
    }

    override fun onClick(v: View?) {
        val title = binding.fragmentAddCurrencyTitle.text.toString()
        presenter.onSaveChosenCurrency(title)
    }
}