package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_admin_tambah_staff.view.*

class AdminTambahStaffFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var namaSekolah: String
    private lateinit var edtNamaStaff: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private var spinners: Spinner? = null
    private lateinit var ref: DatabaseReference
    private lateinit var sekolahList: MutableList<Sekolah>
    private lateinit var namaSekolahList: MutableList<String>

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

        spinners = view?.findViewById(R.id.spinner_tambah_staff)
        spinners?.onItemSelectedListener = this
        ref = FirebaseDatabase.getInstance().getReference("sekolah")
        sekolahList = mutableListOf()
        namaSekolahList = mutableListOf()

        ref.orderByChild("nama_sekolah").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    sekolahList.clear()
                    namaSekolahList.clear()
                    for (s in snapshot.children) {
                        val sekolah = s.getValue(Sekolah::class.java)
                        if (sekolah != null) {
                            namaSekolahList.add(sekolah.nama_sekolah)
                            sekolahList.add(sekolah)
                        }
                    }
                    namaSekolah = namaSekolahList.first()

                    context?.let {
                        val adapter = ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, namaSekolahList)
                        spinners?.adapter = adapter
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        namaSekolah = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private fun insert(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("staff")

        val nama_staff = edtNamaStaff.text.toString().trim()
        val username = edtUsername.text.toString()
        val password = edtPassword.text.toString()
        val id_sekolah = getIdSekolah(namaSekolah)

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

    private fun getIdSekolah(namaSekolah: String): String {
        var id = ""
        for(sekolah in sekolahList) {
            if(sekolah.nama_sekolah.equals(namaSekolah)) {
                id = sekolah.id_sekolah
            }
        }
        return id
    }
}