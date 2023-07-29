package com.example.sellingappkotlin.components.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.components.activities.auth.LoginActivity
import com.example.sellingappkotlin.components.activities.user.ProfileActivity
import com.example.sellingappkotlin.databinding.FragmentPersonBinding
import com.example.sellingappkotlin.models.Detail
import com.example.sellingappkotlin.models.User
import com.example.sellingappkotlin.models.responseApi.ApiResponseUser
import com.example.sellingappkotlin.utils.ApiServiceUser
import com.example.sellingappkotlin.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        getProfile(Constant.token)
        binding.btnLogOut.setOnClickListener {
            callApiLogout(Constant.token)
        }
        binding.profileImage.setOnClickListener{
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        binding.btnEditProfile.setOnClickListener{
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        binding.btnChangePassword.setOnClickListener {

        }
        binding.btnMyOder.setOnClickListener {

        }
    }


    private fun callApiLogout(token: String) {
        ApiServiceUser.apiServiceUser.logout("Bearer $token").enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Log.d("TAG", "onResponse: ${response.toString()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("PersonFragment-callApiLogout-onFailure","${t.message}")
            }
        })
    }

    private fun getProfile(token: String){
        ApiServiceUser.apiServiceUser.getProfile("Bearer $token").enqueue(object : Callback<ApiResponseUser>{
            override fun onResponse(
                call: Call<ApiResponseUser>,
                response: Response<ApiResponseUser>
            ) {
                val result = response.body()
                val user: User= result!!.data
                Glide.with(requireContext()).load(user.avatar).error(R.drawable.avatar_default).into(binding.profileImage)
                binding.tvUsername.text = user.username ?: getString(R.string.username_default)
                binding.tvEmail.text = user.email?: getString(R.string.email_default)
            }

            override fun onFailure(call: Call<ApiResponseUser>, t: Throwable) {
                Log.d("PersonFragment-getProfile-onFailure","${t.message}")
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = PersonFragment().apply {}
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}