package com.winli.aplikasiinformasisekolah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*

class AdminStaffFragment : Fragment() {

    private lateinit var listData: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var staffList: MutableList<Staff>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_staff, container, false)
        ref = FirebaseDatabase.getInstance().getReference("staff")
        listData = view.findViewById(R.id.list_staff)
        staffList = mutableListOf()

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    staffList.clear()
                    for (s in snapshot.children) {
                        val staff = s.getValue(Staff::class.java)
                        if (staff != null) {
                            staffList.add(staff)
                        }
                    }

                    val adapter = context?.let {
                        StaffAdapter(
                            it,
                            R.layout.layout_list_staff, staffList)
                    }
                    listData.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // Inflate the layout for this fragment
        return view
    }

    /*
    private lateinit var listData: RecyclerView
    private lateinit var ref: DatabaseReference
    private lateinit var staffList: MutableList<Staff>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_staff, container, false)
        ref = FirebaseDatabase.getInstance().getReference("staff")
        listData = view.findViewById(R.id.recycler_staff)
        staffList = mutableListOf()

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    staffList.clear()
                    for (s in snapshot.children) {
                        val staff = s.getValue(Staff::class.java)
                        if (staff != null) {
                            staffList.add(staff)
                        }
                    }

                    val adapter = StaffAdapter(this@AdminStaffFragment,
                    R.layout.layout_list_staff, staffList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // Inflate the layout for this fragment
        return view
    }
    */
}