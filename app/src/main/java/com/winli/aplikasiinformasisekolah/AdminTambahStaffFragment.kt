package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_tambah_staff.view.*

class AdminTambahStaffFragment : Fragment() {

    private lateinit var edtNamaStaff: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtIdSekolah: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_tambah_staff, container, false)

        edtNamaStaff = view.findViewById(R.id.edt_nama_staff)
        edtUsername = view.findViewById(R.id.edt_username_staff)
        edtPassword = view.findViewById(R.id.edt_password_staff)

        view.btn_tambah_staff.setOnClickListener{
            insert()
        }
        return view
    }

    private fun insert(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("staff")

        val nama_staff = edtNamaStaff.text.toString().trim()
        val username = edtUsername.text.toString()
        val password = edtPassword.text.toString()
        val id_sekolah = "ID"

        if (nama_staff.isEmpty() or username.isEmpty() or password.isEmpty() or id_sekolah.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val id_staff = ref.push().key
        val staff = Staff(id_staff!!, nama_staff, username, password, id_sekolah)
        ref.child(id_staff).setValue(staff).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}