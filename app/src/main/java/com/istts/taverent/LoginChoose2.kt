package com.istts.taverent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.istts.taverent.databinding.FragmentLoginChoose2Binding


class LoginChoose2 : Fragment() {
    private lateinit var binding: FragmentLoginChoose2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginChoose2Binding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val fragment  = LoginChoose1()
            val bundle = Bundle()
            bundle.putString("tipe","penginap")
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.frag1,fragment)
                .commit()
        }
        binding.button3.setOnClickListener {
            val fragment  = LoginChoose1()
            val bundle = Bundle()
            bundle.putString("tipe","pemilik")
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.frag1,fragment)
                .commit()
        }
    }
}