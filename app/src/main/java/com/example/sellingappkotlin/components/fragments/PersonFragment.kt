package com.example.sellingappkotlin.components.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.sellingappkotlin.R
import com.example.sellingappkotlin.components.activities.auth.LoginActivity
import com.example.sellingappkotlin.components.activities.user.ProfileActivity
import com.example.sellingappkotlin.databinding.FragmentPersonBinding
import com.example.sellingappkotlin.models.Detail
import com.example.sellingappkotlin.models.User
import com.example.sellingappkotlin.models.responseApi.ApiResponseUser
import com.example.sellingappkotlin.utils.ApiServiceUser
import com.example.sellingappkotlin.utils.Config
import com.example.sellingappkotlin.utils.Constant
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Objects

class PersonFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null

    private lateinit var user: User
    private val binding get() = _binding!!

    private val getImageContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            if (selectedImageUri != null) {
                val imagePath = getRealPathFromURI(selectedImageUri)
                if (imagePath != null) {
                    // You can now proceed to upload the selected image to the API
                    uploadImageToApi(imagePath)
                }
            }
        }
    }

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
            val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getImageContent.launch(galleryIntent)
        }
        binding.btnEditProfile.setOnClickListener{
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra("user",user)
            startActivity(intent)
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
                 user = result!!.data
                Glide.with(requireContext()).load(user.avatar.replace("localhost", Config.LOCALHOST)).error(R.drawable.avatar_default).into(binding.profileImage)
                binding.tvUsername.text = user.username ?: getString(R.string.username_default)
                binding.tvEmail.text = user.email?: getString(R.string.email_default)
            }

            override fun onFailure(call: Call<ApiResponseUser>, t: Throwable) {
                Log.d("PersonFragment-getProfile-onFailure","${t.message}")
            }
        })
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return path
    }
    private fun uploadImageToApi(imagePath: String) {
        val imageFile = File(imagePath)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile)
        val imagePart = MultipartBody.Part.createFormData("avatar", imageFile.name, requestFile)

        val id = user._id // Thay thế bằng ID của người dùng
        ApiServiceUser.apiServiceUser.upLoadAvatar(id, imagePart).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(requireActivity(),"update avatar success", Toast.LENGTH_SHORT).show()
                getProfile(Constant.token)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireActivity(),"update avatar fail", Toast.LENGTH_SHORT).show()
                Log.d( "uploadImageToApi-onFailure", t.message.toString())
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

    override fun onResume() {
        super.onResume()
        getProfile(Constant.token)
    }
}