package com.example.sellingappkotlin.components.activities.user

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.Toast
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.databinding.ActivityProfileBinding
import com.example.sellingappkotlin.models.User
import com.example.sellingappkotlin.utils.ApiServiceUser
import com.example.sellingappkotlin.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val calendar = Calendar.getInstance()
    private var fullName: String = ""
    private var username: String = ""
    private var phoneNumber: String = ""
    private var birthday: String = ""
    private var gender: Int = 0
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        user = intent.getSerializableExtra("user") as User

        Log.d("AAA", user.toString())

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.tvTitle.text = getString(R.string.my_profile)

        binding.edtPhone.setText(user.phone) ?: ""
        binding.edtUsername.setText(user.username) ?: ""
        binding.edtFullName.setText(user.fullName) ?: ""
        binding.selectBirthday.text = user.birthday
        if (user.gender == 1) {
            binding.radioFemale.isChecked = true
        } else {
            binding.radioMale.isChecked = true
        }


        binding.layoutSelectDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            Log.d("click", "pressed layout")
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val dateFormat =
                        java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    birthday = dateFormat.format(calendar.time)
                    binding.selectBirthday.text = birthday
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        binding.radioGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_male -> {
                    gender = 0
                }

                R.id.radio_female -> {
                    gender = 1
                }
            }
        }

        binding.btnSave.setOnClickListener {
            fullName = binding.edtFullName.text.toString()
            username = binding.edtUsername.text.toString()
            phoneNumber = binding.edtPhone.text.toString()

            user = User(user._id, fullName,username, user.email,user.password,user.address?:"", gender, user.role, phoneNumber, user.avatar?:"", birthday, user.token)
            Log.d("Update", user.toString())

            updateProfile(user._id, user)

        }

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }


    }

    private fun updateProfile(id: String, user: User) {
        ApiServiceUser.apiServiceUser.updateProfile(id, "Bearer ${Constant.token}", user).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@ProfileActivity,"update profile success", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ProfileActivity,"update profile fail", Toast.LENGTH_SHORT).show()
            }
        })
    }


}