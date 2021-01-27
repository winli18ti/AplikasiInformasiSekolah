package com.winli.aplikasiinformasisekolah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class StaffAdapter(
    val staffContext: Context,
    val layoutResId: Int,
    val staffList: List<Staff>
): ArrayAdapter<Staff>(staffContext, layoutResId, staffList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(staffContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama_staff: TextView = view.findViewById(R.id.txt_nama_staff)
        val username: TextView = view.findViewById(R.id.txt_username_staff)
        val password: TextView = view.findViewById(R.id.txt_password_staff)
        val id_sekolah: TextView = view.findViewById(R.id.txt_id_sekolah)

        val staff = staffList[position]

        nama_staff.text = "Nama : " + staff.nama_staff
        username.text = "Username : " + staff.username
        password.text = "Password : " + staff.password
        id_sekolah.text = "Id Sekolah : " + staff.id_sekolah

        return view
    }
}