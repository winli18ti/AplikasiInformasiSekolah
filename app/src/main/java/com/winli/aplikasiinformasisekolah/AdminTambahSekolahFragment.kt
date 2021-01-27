package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_admin_tambah_sekolah.view.*

class AdminTambahSekolahFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etKeterangan: EditText
    private lateinit var etKoordinatx: EditText
    private lateinit var etKoordinaty: EditText
    private lateinit var etGambar: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_tambah_sekolah, container, false)

        etName = view.findViewById(R.id.et_name)
        etKeterangan = view.findViewById(R.id.et_keterangan)
        etKoordinatx = view.findViewById(R.id.et_koordinat_x)
        etKoordinaty = view.findViewById(R.id.et_koordinat_y)
        etGambar = view.findViewById(R.id.et_gambar)

        view.btn_tambah_sekolah.setOnClickListener{
            insert()
        }
        return view
    }
    private fun insert(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")

        val nama_sekolah = etName.text.toString().trim()
        val keterangan = etKeterangan.text.toString()
        val koordinatX = etKoordinatx.text.toString()
        val koordinatY = etKoordinaty.text.toString()
        val gambar = etGambar.text.toString()

        if (nama_sekolah.isEmpty() or keterangan.isEmpty() or koordinatX.isEmpty() or koordinatY.isEmpty() or gambar.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val id_sekolah = ref.push().key
        val sekolah = Sekolah(id_sekolah!!, nama_sekolah,gambar, keterangan, koordinatX, koordinatY)
        ref.child(id_sekolah).setValue(sekolah).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}