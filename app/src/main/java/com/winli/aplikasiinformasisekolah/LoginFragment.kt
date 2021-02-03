package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*
import com.google.firebase.database.*

class LoginFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var level: String = "Staff"
    private lateinit var refStaff: DatabaseReference
    private lateinit var refAdmin: DatabaseReference
    private lateinit var staffList: MutableList<Staff>
    private lateinit var adminList: MutableList<Admin>
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        edtUsername = view.findViewById(R.id.edt_username)
        edtPassword = view.findViewById(R.id.edt_password)

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

        getData()

        return view
    }

    private fun getData() {
        refStaff = FirebaseDatabase.getInstance().getReference("staff")
        staffList = mutableListOf()

        refStaff.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    staffList.clear()
                    for (s in snapshot.children) {
                        val staff = s.getValue(Staff::class.java)
                        if (staff != null) {
                            staffList.add(staff)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        refAdmin = FirebaseDatabase.getInstance().getReference("admin")
        adminList = mutableListOf()

        refAdmin.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    adminList.clear()
                    for (s in snapshot.children) {
                        val admin = s.getValue(Admin::class.java)
                        if (admin != null) {
                            adminList.add(admin)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        level = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private fun login() {
        var username = edtUsername.text.toString()
        var password = edtPassword.text.toString()

        if(username.isEmpty() or password.isEmpty()) {
            Toast.makeText(context, "Username atau Password kosong", Toast.LENGTH_SHORT)
                .show()
            return
        }
        var id = ""

        if(level.equals("Staff")) {
            for (staff in staffList) {
                if(username.equals(staff.username) and password.equals(staff.password)) {
                    id = staff.id_staff
                    break
                }
            }
            if(id.isEmpty()) {
                Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT)
                    .show()
                return
            }
            else {
                val bundle = bundleOf("idStaff" to id)
                findNavController().navigate(R.id.action_loginFragment_to_staffHome, bundle)
                return
            }
        }
        else if(level.equals("Admin")){
            for (admin in adminList) {
                if(username.equals(admin.username) and password.equals(admin.password)) {
                    id = admin.id_admin
                    break
                }
            }
            if(id.isEmpty()) {
                Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT)
                    .show()
                return
            }
            else {
                findNavController().navigate(R.id.action_loginFragment_to_adminHome)
                return
            }
        }
    }
}