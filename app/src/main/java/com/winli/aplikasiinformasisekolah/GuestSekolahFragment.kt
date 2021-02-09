package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_guest_sekolah.view.*

class GuestSekolahFragment : Fragment() {

    private lateinit var edtCariSekolah: EditText
    private lateinit var listData: ListView
    private lateinit var sekolahList: MutableList<Sekolah>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_sekolah, container, false)
        edtCariSekolah = view.findViewById(R.id.edt_cari_sekolah)
        listData = view.findViewById(R.id.list_sekolah)
        sekolahList = mutableListOf()

        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")
        ref.orderByChild("nama_sekolah").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    sekolahList.clear()
                    for (s in snapshot.children) {
                        val sekolah = s.getValue(Sekolah::class.java)
                        if (sekolah != null) {
                            sekolahList.add(sekolah)
                        }
                    }

                    val adapter = context?.let {
                        SekolahAdapter(
                            it,
                            R.layout.layout_list_sekolah,
                            sekolahList,
                            "Guest")
                    }
                    listData.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        view.btn_cari_sekolah.setOnClickListener {
            cari()
        }

        return view
    }

    private fun cari() {
        var query = edtCariSekolah.text.toString()
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")
        ref.orderByChild("nama_sekolah").startAt(query).endAt(query+"\uf8ff")
            .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    sekolahList.clear()
                    for (s in snapshot.children) {
                        val sekolah = s.getValue(Sekolah::class.java)
                        if (sekolah != null) {
                            sekolahList.add(sekolah)
                        }
                    }

                    val adapter = context?.let {
                        SekolahAdapter(
                            it,
                            R.layout.layout_list_sekolah,
                            sekolahList,
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
}