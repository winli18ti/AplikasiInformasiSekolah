package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*
import com.google.firebase.database.*
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView

class LoginFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var level: String = "Staff"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val spnLevel: Spinner = view.findViewById(R.id.spn_level)

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.level,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnLevel.adapter = adapter
            }
        }

        spnLevel.onItemSelectedListener = this

        view.btn_login.setOnClickListener {
            login()
        }

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        level = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private fun login() {
        if(level.equals("Staff")) {
            var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("staff")

            findNavController().navigate(R.id.action_loginFragment_to_staffHome)
        }
        else if(level.equals("Admin")){
            var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("admin")

            findNavController().navigate(R.id.action_loginFragment_to_adminHome)
        }
    }
}