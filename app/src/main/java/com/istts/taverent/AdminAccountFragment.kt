package com.istts.taverent

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.istts.taverent.databinding.FragmentAdminAccountBinding


class AdminAccountFragment : Fragment() {
    private lateinit var binding: FragmentAdminAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminAccountBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button9.setOnClickListener {
            val intent = Intent(view.context,LoginActivity::class.java)
            activity?.runOnUiThread { startActivity(intent) }
        }
    }
}