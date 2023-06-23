package com.example.sellingappkotlin.components.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sellingappkotlin.components.activities.LoginActivity
import com.example.sellingappkotlin.databinding.FragmentPersonBinding

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

        binding.btnLogOut.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

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