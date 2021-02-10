package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_guest_berita.view.*

class GuestBeritaFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var listData: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var beritaList: MutableList<Berita>
    private var spinners: Spinner? = null
    private lateinit var refSekolah: DatabaseReference
    private lateinit var sekolahList: MutableList<Sekolah>
    private lateinit var namaSekolahList: MutableList<String>
    private lateinit var namaSekolah: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_berita, container, false)
        ref = FirebaseDatabase.getInstance().getReference("berita")
        listData = view.findViewById(R.id.list_berita_guest)
        beritaList = mutableListOf()

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    beritaList.clear()
                    for (s in snapshot.children) {
                        val berita = s.getValue(Berita::class.java)
                        if (berita != null) {
                            beritaList.add(berita)
                        }
                    }

                    val adapter = context?.let {
                        BeritaAdapter(
                            it,
                            R.layout.layout_list_berita,
                            beritaList.reversed(),
                            "Guest")
                    }
                    listData.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        spinners = view?.findViewById(R.id.spinner_berita_sekolah)
        spinners?.onItemSelectedListener = this
        refSekolah = FirebaseDatabase.getInstance().getReference("sekolah")
        sekolahList = mutableListOf()
        namaSekolahList = mutableListOf()

        refSekolah.orderByChild("nama_sekolah").addValueEventListener(object: ValueEventListener{
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

        view.btn_cari_berita.setOnClickListener {
            cari()
        }

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        namaSekolah = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private fun cari() {
        val idSekolah = getIdSekolah(namaSekolah)

        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("berita")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    beritaList.clear()
                    for (s in snapshot.children) {
                        val berita = s.getValue(Berita::class.java)
                        if (berita != null && berita.id_sekolah.equals(idSekolah)) {
                            beritaList.add(berita)
                        }
                    }

                    val adapter = context?.let {
                        BeritaAdapter(
                            it,
                            R.layout.layout_list_berita,
                            beritaList.reversed(),
                            "Guest")
                    }
                    listData.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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