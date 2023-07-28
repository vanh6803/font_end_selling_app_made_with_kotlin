package com.example.sellingappkotlin.components.activities.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.components.activities.MainActivity
import com.example.sellingappkotlin.databinding.ActivityLoginBinding
import com.example.sellingappkotlin.models.Account
import com.example.sellingappkotlin.models.responseApi.ApiResponseLogin
import com.example.sellingappkotlin.utils.ApiServiceUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val backPressInterval = 2000 //Time allowed between two back presses (2 seconds)
    private var doubleBackToExitPressedOnce: Boolean = false
    private var handle = Handler(Looper.getMainLooper())
    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idTitleSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            startActivity(intent, options.toBundle())
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            account = Account(email, password, 1)
            if (email.isEmpty() || password.isEmpty()){
                binding.errorEmail.visibility = View.VISIBLE
                binding.errorPassword.visibility = View.VISIBLE
                binding.errorEmail.text = getString(R.string.must_not_be_left_blank)
                binding.errorPassword.text = getString(R.string.must_not_be_left_blank)
            }else{
                callApiLogin(account)
            }
        }
    }

    private fun callApiLogin(account: Account) {
        ApiServiceUser.apiServiceUser.login(account).enqueue(object : Callback<ApiResponseLogin> {
            override fun onResponse(
                call: Call<ApiResponseLogin>,
                response: Response<ApiResponseLogin>
            ) {
                val result = response.body()
                val message = result?.message
                Log.d("result", "${response.body().toString()}")
                if (response.isSuccessful) {
                    binding.errorEmail.visibility = View.GONE
                    binding.errorPassword.visibility = View.GONE
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {

                    binding.errorEmail.visibility = View.VISIBLE
                    binding.errorPassword.visibility = View.VISIBLE
                    binding.errorEmail.text = getString(R.string.wrong_account_or_password)
                    binding.errorPassword.text = getString(R.string.wrong_account_or_password)
                }
            }

            override fun onFailure(call: Call<ApiResponseLogin>, t: Throwable) {
                Log.d("callApiLogin-onFailure", t.message.toString())
            }
        })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity() // clear all activities in the stack
            exitProcess(0) //Quit the app
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            getString(R.string.press_back_one_more_time_to_exit),
            Toast.LENGTH_SHORT
        ).show()
        handle.postDelayed({
            kotlin.run {
                doubleBackToExitPressedOnce = false
            }
        }, backPressInterval.toLong())
    }
}