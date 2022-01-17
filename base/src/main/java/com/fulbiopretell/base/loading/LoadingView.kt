package com.fulbiopretell.retobcp.base.loading

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fulbiopretell.base.R

class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate()
    }

    private fun inflate() {
        LayoutInflater.from(context).inflate(R.layout.loading, this)
    }

    fun removeFromParent() {
        this.parent?.let {
            (it as ViewGroup).removeView(this)
        }
    }
}
