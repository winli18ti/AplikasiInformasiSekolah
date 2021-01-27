package com.winli.aplikasiinformasisekolah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AdminAdapter(
    val adminContext: Context,
    val layoutResId: Int,
    val adminList: List<Admin>
): ArrayAdapter<Admin>(adminContext, layoutResId, adminList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(adminContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val username: TextView = view.findViewById(R.id.txt_username_admin)
        val password: TextView = view.findViewById(R.id.txt_password_admin)

        val admin = adminList[position]

        username.text = "Username : " + admin.username
        password.text = "Password : " + admin.password
        return view
    }
}