package com.fulbiopretell.retobcp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.fulbiopretell.base.BaseActivity
import com.fulbiopretell.retobcp.ui.main.MainActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }, 2000)
    }
}