package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*

class GuestSekolahFragment : Fragment() {

    private lateinit var listData: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var sekolahList: MutableList<Sekolah>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_sekolah, container, false)
        ref = FirebaseDatabase.getInstance().getReference("sekolah")
        listData = view.findViewById(R.id.list_sekolah)
        sekolahList = mutableListOf()

        ref.addValueEventListener(object: ValueEventListener {
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
                            R.layout.layout_list_sekolah, sekolahList)
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