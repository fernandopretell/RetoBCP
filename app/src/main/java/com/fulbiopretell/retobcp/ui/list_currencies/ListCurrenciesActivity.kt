package com.fulbiopretell.retobcp.ui.list_currencies

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fulbiopretell.base.BaseActivity
import com.fulbiopretell.core.Constants.ConstantsCore.GET
import com.fulbiopretell.core.Constants.ConstantsCore.SEND
import com.fulbiopretell.core.models.CurrencyLocalModel
import com.fulbiopretell.retobcp.adapters.CurrenciesAdapter
import com.fulbiopretell.retobcp.constants.Constants.CURRENCY_FROM
import com.fulbiopretell.retobcp.constants.Constants.CURRENCY_TO
import com.fulbiopretell.retobcp.constants.Constants.DATA
import com.fulbiopretell.retobcp.constants.Constants.NEW_CURRENCY_FROM
import com.fulbiopretell.retobcp.constants.Constants.NEW_CURRENCY_TO
import com.fulbiopretell.retobcp.constants.Constants.TYPE
import com.fulbiopretell.retobcp.databinding.ActivityListCurrenciesBinding
import com.fulbiopretell.retobcp.models.GetTypeExchange
import com.fulbiopretell.retobcp.util.Util.getCountryCode
import com.fulbiopretell.retobcp.viewmodels.ListCurrenciesViewModel
import com.google.gson.Gson

class ListCurrenciesActivity : BaseActivity() {

    private lateinit var binding: ActivityListCurrenciesBinding
    private lateinit var viewmodel: ListCurrenciesViewModel

    private var sendOrGet: String? = null
    private var currencyBase: String? = null
    private var currencyTo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCurrenciesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
        initViewModel()
        setupRecyclerview()
    }

    private fun setupData() {
        sendOrGet = intent.getStringExtra(TYPE)
        currencyBase = intent.getStringExtra(CURRENCY_FROM)
        currencyTo = intent.getStringExtra(CURRENCY_TO)
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this).get(ListCurrenciesViewModel::class.java)
    }

    private fun setupRecyclerview() {

        val listener = object : CurrenciesAdapter.CurrenciesAdapterListener {
            override fun onclickItem(item: CurrencyLocalModel) {
                callApiTypeExchange(item.currency.code)
            }
        }

        val adapter = CurrenciesAdapter(listener, this)
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = adapter
        adapter.updateData(getCountryCode(this))
    }

    private fun callApiTypeExchange(code: String) {
        val modelTypeExchange = GetTypeExchange()

        when(sendOrGet) {
            SEND -> {
                modelTypeExchange.from = code
                modelTypeExchange.to = currencyTo
            }

            GET -> {
                modelTypeExchange.from = currencyBase
                modelTypeExchange.to = code
            }
        }

        viewmodel.getTypeExchange(modelTypeExchange).observe(this, {
            when (it) {
                is ListCurrenciesViewModel.ViewState.FetchSuccess -> {
                    showLoading(false)

                    val newCurrencyFrom = getCountryCode(this).filter { it.currency.code == modelTypeExchange.from }.first()
                    val newCurrencyTo = getCountryCode(this).filter { it.currency.code == modelTypeExchange.to }.first()

                    val data = Intent()
                    data.putExtra(DATA, Gson().toJson(it.response))
                    data.putExtra(NEW_CURRENCY_FROM, Gson().toJson(newCurrencyFrom).toString())
                    data.putExtra(NEW_CURRENCY_TO, Gson().toJson(newCurrencyTo).toString())
                    setResult(RESULT_OK, data)
                    finish()
                }

                is ListCurrenciesViewModel.ViewState.FetchFailure -> {
                    showLoading(false)
                    Toast.makeText(this, "Problemas al ejecutar el servicio", Toast.LENGTH_SHORT).show()
                }

                is ListCurrenciesViewModel.ViewState.Loading -> {
                    showLoading(true)
                }
            }
        })
    }
}