package com.example.sellingappkotlin.components.activities.auth

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivitySignUpBinding
import com.example.sellingappkotlin.models.Account
import com.example.sellingappkotlin.models.CheckEmail
import com.example.sellingappkotlin.models.responseApi.ApiResponseAccount
import com.example.sellingappkotlin.utils.ApiServiceUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var doneDialog: Dialog
    private lateinit var account: Account
    private lateinit var list: MutableList<Account>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idTitleLogin.setOnClickListener {
            nextActivity()
        }

        binding.btnSignup.setOnClickListener {
            var email:String = binding.edtEmail.text.toString()
            var password:String = binding.edtPassword.text.toString()
            var rePassword: String = binding.edtRePassword.text.toString()
            account = Account(email, password, 1)
            if (checkValidate(email, password, rePassword)){
                callApiRegisterAccount(account)
            }
        }

    }

    private fun callApiRegisterAccount(account: Account){
        ApiServiceUser.apiServiceUser.register(account).enqueue(object: Callback<Account>{
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                showDialogDone()
                handler.postDelayed({
                    doneDialog.dismiss()
                    nextActivity()
                },3000)
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {

            }
        })
    }

    private suspend fun checkEmail(email: String): Boolean {
        var check: Boolean = false
        runBlocking {
            val response = withContext(Dispatchers.IO){
                ApiServiceUser.apiServiceUser.checkEmail(email).execute()
            }
            if (response.isSuccessful){
                val checkEmailResponse = response.body()
                check = checkEmailResponse?.check ?:false
            }
        }
        return check
    }

    private fun checkValidate(email: String, password: String, rePassword: String): Boolean{

        if (email.isEmpty() || password.isEmpty() || rePassword.isEmpty()){
            return false
        }
        // check email
        val isEmailExists = runBlocking {
            checkEmail(email)
        }
        if (isEmailExists) {
            Log.d("checkValidate", "Email đã tồn tại.")
            return false
        }

        // check password
        if (password.length < 6){
            return false
        }

        if (rePassword!= password){
            return false
        }

        return true
    }

    private fun showDialogDone(){
        doneDialog = Dialog(this)
        doneDialog.setContentView(R.layout.layout_dialog_done)
        doneDialog.setCancelable(false)
        doneDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        doneDialog.show()
    }
    private fun nextActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        val options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left)
        startActivity(intent, options.toBundle())
    }
}