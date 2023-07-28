package com.example.sellingappkotlin.components.activities.auth

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivitySignUpBinding
import com.example.sellingappkotlin.models.Account
import com.example.sellingappkotlin.utils.ApiServiceUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var doneDialog: Dialog
    private lateinit var account: Account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idTitleLogin.setOnClickListener {
            nextActivity()
        }

        binding.btnSignup.setOnClickListener {
            val email: String = binding.edtEmail.text.toString()
            val password: String = binding.edtPassword.text.toString()
            val rePassword: String = binding.edtRePassword.text.toString()
            account = Account(email, password, 1)
            if (checkValidate(email, password, rePassword) && binding.checkBox.isChecked) {
                binding.errorCheck.visibility = View.GONE
                binding.errorEmail.visibility = View.GONE
                binding.errorPassword.visibility = View.GONE
                binding.errorRePassword.visibility = View.GONE
                callApiRegisterAccount(account)
            }else if (!binding.checkBox.isChecked){
                binding.errorCheck.visibility = View.VISIBLE
                binding.errorCheck.text = getString(R.string.required)
            }
        }

    }

    private fun callApiRegisterAccount(account: Account) {
        ApiServiceUser.apiServiceUser.register(account).enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                showDialogDone()
                handler.postDelayed({
                    doneDialog.dismiss()
                    nextActivity()
                }, 3000)
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {

            }
        })
    }

    private suspend fun checkEmail(email: String): Boolean {
        var check = false
        runBlocking {
            val response = withContext(Dispatchers.IO) {
                ApiServiceUser.apiServiceUser.checkEmail(email).execute()
            }
            if (response.isSuccessful) {
                val checkEmailResponse = response.body()
                check = checkEmailResponse?.check ?: false
            }
        }
        return check
    }

    private fun checkValidate(email: String, password: String, rePassword: String): Boolean {

        //check empty
        if (email.isEmpty()) {
            binding.errorEmail.text = getString(R.string.please_enter_email)
            binding.errorEmail.visibility = View.VISIBLE
            return false
        }
        if (password.isEmpty()) {
            binding.errorPassword.text = getString(R.string.please_enter_password)
            binding.errorPassword.visibility = View.VISIBLE
            return false
        }
        if (rePassword.isEmpty()) {
            binding.errorRePassword.text = getString(R.string.please_re_enter_password)
            binding.errorRePassword.visibility = View.VISIBLE
            return false
        }
        // check email
        val isEmailExists = runBlocking {
            checkEmail(email)
        }
        if (isEmailExists) {
            binding.errorEmail.text = getString(R.string.email_already_exists)
            binding.errorEmail.visibility = View.VISIBLE
            return false
        }

        // check password
        if (password.length < 6) {
            binding.errorPassword.text = getString(R.string.password_can_more_than_6_characters)
            binding.errorPassword.visibility = View.VISIBLE
            return false
        }

        if (rePassword != password) {
            binding.errorRePassword.text = getString(R.string.please_enter_the_correct_password)
            binding.errorRePassword.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun showDialogDone() {
        doneDialog = Dialog(this)
        doneDialog.setContentView(R.layout.layout_dialog_done)
        doneDialog.setCancelable(false)
        doneDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        doneDialog.show()
    }

    private fun nextActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        startActivity(intent, options.toBundle())
    }
}