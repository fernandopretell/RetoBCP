package com.fulbiopretell.retobcp.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView

fun AutoCompleteTextView.onTextChanged(event: (String) -> Unit = { }) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { event.invoke(it.toString()) }
        }
    })
}
