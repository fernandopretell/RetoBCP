package com.fulbiopretell.retobcp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.fulbiopretell.base.BaseActivity
import com.fulbiopretell.base_ui.display_convertion.DisplayConvertionComponent
import com.fulbiopretell.core.models.CurrencyLocalModel
import com.fulbiopretell.retobcp.constants.Constants.CURRENCY_FROM
import com.fulbiopretell.retobcp.constants.Constants.CURRENCY_TO
import com.fulbiopretell.retobcp.constants.Constants.DATA
import com.fulbiopretell.retobcp.constants.Constants.NEW_CURRENCY_FROM
import com.fulbiopretell.retobcp.constants.Constants.NEW_CURRENCY_TO
import com.fulbiopretell.retobcp.constants.Constants.TYPE
import com.fulbiopretell.retobcp.databinding.ActivityMainBinding
import com.fulbiopretell.retobcp.models.ApiResponse
import com.fulbiopretell.retobcp.ui.list_currencies.ListCurrenciesActivity
import com.fulbiopretell.retobcp.util.Util
import com.google.gson.Gson
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    //por regla general el valor de venta sera 4 puntos por encima del tipo de cambio, que será el valor de compra
    private var typeExchange: Double = 3.87
    private var isChangedActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initListeners()
    }

    private fun initViews() {
        //se establecen los valores iniciales
        val currencyDol = Util.getCountryCode(this).filter { it.name == "United States" }.first()
        val currencyPen = Util.getCountryCode(this).filter { it.name == "Peru" }.first()

        binding.displayConvertionComponent.setValuePurchaseAndSell(typeExchange)
        binding.displayConvertionComponent.setCurrrencies(currencyDol, currencyPen)
        updateExchangeRate(typeExchange)
    }

    private fun initListeners() {

        binding.btnStartOperation.setOnClickListener {
            Toast.makeText(this, "Sin acciones", Toast.LENGTH_SHORT).show()
        }

        binding.displayConvertionComponent.listener = object : DisplayConvertionComponent.DisplayConvertionListener {
            override fun onClickChange(v: View, isChangedActivated: Boolean) {
                isChangedActive = isChangedActivated
                binding.displayConvertionComponent.setValuePurchaseAndSell(typeExchange)
                updateExchangeRate(typeExchange)

            }

            override fun onLongClick(v: View, sendOrGet: String, valueBase: String?, valueTo: String?) {
                val intent = Intent(this@MainActivity, ListCurrenciesActivity::class.java)
                intent.putExtra(TYPE, sendOrGet)
                intent.putExtra(CURRENCY_FROM, valueBase)
                intent.putExtra(CURRENCY_TO, valueTo)
                resultLauncherListCurrencies.launch(intent)
            }
        }
    }

    private fun updateExchangeRate(typeExchange: Double) {
        if (isChangedActive) {
            val typeExchangePurchase = String.format("%.4f", 1 / typeExchange)
            val typeExchangeSell = String.format("%.4f", 1 / (typeExchange + 0.04))
            "Compra: $typeExchangePurchase  |  Venta: $typeExchangeSell".also { binding.exchangeRate.text = it }
        } else {
            "Compra: ${typeExchange}  |  Venta: ${(typeExchange + 0.04)}".also { binding.exchangeRate.text = it }
        }
    }

    var resultLauncherListCurrencies = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newCurrencyFrom = Gson().fromJson(result.data?.getStringExtra(NEW_CURRENCY_FROM), CurrencyLocalModel::class.java)
            val newCurrencyTo = Gson().fromJson(result.data?.getStringExtra(NEW_CURRENCY_TO), CurrencyLocalModel::class.java)
            val data = Gson().fromJson(result.data?.getStringExtra(DATA), ApiResponse::class.java)

            typeExchange = data.result?.get(newCurrencyTo.currency.code)?.asDouble ?: 0.0
            binding.displayConvertionComponent.setValuePurchaseAndSell(typeExchange)
            binding.displayConvertionComponent.setCurrrencies(newCurrencyFrom, newCurrencyTo)
            updateExchangeRate(typeExchange)
        }
    }
}