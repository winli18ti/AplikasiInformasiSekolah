package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_tambah_admin.view.*

class AdminTambahAdminFragment : Fragment() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_tambah_admin, container, false)

        edtUsername = view.findViewById(R.id.edt_username_admin)
        edtPassword = view.findViewById(R.id.edt_password_admin)

        view.btn_tambah_admin.setOnClickListener{
            insert()
        }
        return view
    }

    private fun insert(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("admin")

        val username = edtUsername.text.toString()
        val password = edtPassword.text.toString()

        if (username.isEmpty() or password.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val id_admin = ref.push().key
        val admin = Admin(id_admin!!, username, password)
        ref.child(id_admin).setValue(admin).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}