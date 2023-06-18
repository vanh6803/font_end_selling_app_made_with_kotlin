package com.example.sellingappkotlin.components.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var doneDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idTitleLogin.setOnClickListener {
            nextActivity()
        }

        binding.btnSignup.setOnClickListener {
            showDialogDone()
            handler.postDelayed({
                doneDialog.dismiss()
                nextActivity()
            },3000)

        }

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