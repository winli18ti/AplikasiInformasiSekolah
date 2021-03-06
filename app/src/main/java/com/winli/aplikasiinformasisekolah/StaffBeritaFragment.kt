package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.*

class StaffBeritaFragment(val idStaff: String) : Fragment() {

    private lateinit var listData: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var beritaList: MutableList<Berita>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff_berita, container, false)
        ref = FirebaseDatabase.getInstance().getReference("berita")
        listData = view.findViewById(R.id.list_berita)
        beritaList = mutableListOf()

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    beritaList.clear()
                    for (s in snapshot.children) {
                        val berita = s.getValue(Berita::class.java)
                        if (berita != null && berita.id_staff.equals(idStaff)) {
                            beritaList.add(berita)
                        }
                    }

                    val adapter = context?.let {
                        BeritaAdapter(
                            it,
                            R.layout.layout_list_berita,
                            beritaList.reversed(),
                            "Staff")
                    }
                    listData.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return view
    }
}