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
import kotlinx.android.synthetic.main.fragment_staff_berita_input.view.*

class StaffBeritaInputFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etIsi: EditText
    private lateinit var etWaktu: EditText
    private lateinit var etGambar: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff_berita_input, container, false)

        etName = view.findViewById(R.id.et_name)
        etIsi = view.findViewById(R.id.et_isi)
        etWaktu = view.findViewById(R.id.et_waktu)
        //etGambar = view.findViewById(R.id.et_gambar)

        view.btn_tambah_berita.setOnClickListener{
            insert()
        }
        return view
    }
    private fun insert(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("berita")

        val nama_berita = etName.text.toString().trim()
        val isi = etIsi.text.toString()
        val waktu = etWaktu.text.toString()
        //val gambar = etGambar.text.toString()

        if (nama_berita.isEmpty() or isi.isEmpty() or waktu.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val id_berita = ref.push().key
        val berita = Berita(id_berita!!, nama_berita, isi,waktu, "")
        ref.child(id_berita).setValue(berita).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}