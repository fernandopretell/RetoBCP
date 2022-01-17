package com.fulbiopretell.base_ui.display_convertion

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.fulbiopretell.base_ui.button.Button
import com.fulbiopretell.base_ui.databinding.DisplayConvertionBinding
import com.fulbiopretell.core.Constants.ConstantsCore.GET
import com.fulbiopretell.core.Constants.ConstantsCore.SEND
import com.fulbiopretell.core.models.CurrencyLocalModel
import com.fulbiopretell.retobcp.core.extensions.cleanText
import com.fulbiopretell.retobcp.core.extensions.clear
import com.fulbiopretell.retobcp.core.extensions.isNotNullOrBlank

class DisplayConvertionComponent : CardView {

    private var binding: DisplayConvertionBinding
    private var typeExchange: Double? = null

    private var currencySend: CurrencyLocalModel? = null
    private var currencyGet: CurrencyLocalModel? = null

    private var isChangedActive = false

    var listener: DisplayConvertionListener? = null

    constructor(context: Context) : super(context) {
        binding = DisplayConvertionBinding.inflate(LayoutInflater.from(context), this, true)
        setupUI()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        binding = DisplayConvertionBinding.inflate(LayoutInflater.from(context), this, true)
        setupUI()
    }

    private fun setupUI() {

        binding.etYouSend.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(binding.etYouSend.isFocused){
                    if (s.isNotNullOrBlank()) {
                        if(isChangedActive){
                            binding.etYouGet.setText((binding.etYouSend.text.toString().toDouble() / typeExchange!! + 0.04).toString())
                        }else{
                            binding.etYouGet.setText((binding.etYouSend.text.toString().toDouble() * typeExchange!!).toString())
                        }
                    }else{
                        binding.etYouGet.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        binding.ivChange.setOnClickListener {
            binding.etYouSend.clear()
            if (isChangedActive){
                binding.tvCurrencyYouSend.setText(currencySend?.currency?.name!!)
                binding.tvCurrencyYouGet.setText(currencyGet?.currency?.name!!)
                isChangedActive = false
            }else{
                binding.tvCurrencyYouSend.setText(currencyGet?.currency?.name!!)
                binding.tvCurrencyYouGet.setText(currencySend?.currency?.name!!)
                isChangedActive = true
            }
            listener?.onClickChange(it, isChangedActive)
        }

        binding.tvCurrencyYouSend.buttonClickListener = object : Button.OnClickListener{
            override fun onClick(view: View) {}

            override fun onLongClick(view: View) {
                listener?.onLongClick(view, SEND, currencySend?.currency?.code, currencyGet?.currency?.code)
            }
        }

        binding.tvCurrencyYouGet.buttonClickListener = object : Button.OnClickListener{
            override fun onClick(view: View) {}

            override fun onLongClick(view: View) {
                listener?.onLongClick(view, GET, currencySend?.currency?.code, currencyGet?.currency?.code)
            }
        }
    }

    fun setValuePurchaseAndSell(typeExchange: Double){
        this.typeExchange = typeExchange
    }

    fun setCurrrencies(currencySend: CurrencyLocalModel, currencyGet: CurrencyLocalModel){
        this.currencySend = currencySend
        this.currencyGet = currencyGet

        binding.tvCurrencyYouSend.setText(currencySend.currency.name)
        binding.tvCurrencyYouGet.setText(currencyGet.currency.name)
    }

    interface DisplayConvertionListener {
        fun onClickChange(v: View, isChangedActivated: Boolean)
        fun onLongClick(v: View, sendOrGet: String, valueBase: String?, valueTo: String?)
    }
}