package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.btn_guest.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_guestHome)
        }

        view.btn_halaman_login.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        return view
    }
}