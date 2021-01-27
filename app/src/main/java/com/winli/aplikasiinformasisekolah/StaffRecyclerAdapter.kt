package com.winli.aplikasiinformasisekolah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.layout_list_staff.view.*

class StaffRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var ref: DatabaseReference
    private lateinit var staffList: MutableList<Staff>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StaffViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_staff, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StaffViewHolder -> {
                holder.bind(staffList.get(position))
                //holder.klik.setOnClickListener
            }
        }
    }

    fun buatList() {
        ref = FirebaseDatabase.getInstance().getReference("staff")
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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    class StaffViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nama_staff: TextView = itemView.txt_nama_staff
        val username: TextView = itemView.txt_username_staff
        val password: TextView = itemView.txt_password_staff
        val id_sekolah: TextView = itemView.txt_id_sekolah

        fun bind(staff: Staff) {
            nama_staff.setText("Nama : " + staff.nama_staff)
            username.setText("Username : " + staff.username)
            password.setText("Password : " + staff.password)
            id_sekolah.setText("Id Sekolah : " + staff.id_sekolah)
        }
    }
}