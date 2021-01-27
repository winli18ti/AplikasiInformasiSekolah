package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*

class AdminAdminFragment : Fragment() {

    private lateinit var listData: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var adminList: MutableList<Admin>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_admin, container, false)
        ref = FirebaseDatabase.getInstance().getReference("admin")
        listData = view.findViewById(R.id.list_admin)
        adminList = mutableListOf()

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    adminList.clear()
                    for (a in snapshot.children) {
                        val admin = a.getValue(Admin::class.java)
                        if (admin != null) {
                            adminList.add(admin)
                        }
                    }

                    val adapter = context?.let {
                        AdminAdapter(
                            it,
                            R.layout.layout_list_admin, adminList)
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