package com.winli.aplikasiinformasisekolah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class SekolahAdapter(
    val sekolahContext: Context,
    val layoutResId: Int,
    val sekolahList: List<Sekolah>
): ArrayAdapter<Sekolah>(sekolahContext, layoutResId, sekolahList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(sekolahContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama_sekolah: TextView = view.findViewById(R.id.txt_nama_sekolah)
        //val gambar_sekolah: TextView = view.findViewById(R.id.img_sekolah)
        val keterangan: TextView = view.findViewById(R.id.txt_isi_sekolah)
        val x: TextView = view.findViewById(R.id.txt_isi_x)
        val y: TextView = view.findViewById(R.id.txt_isi_y)

        val sekolah = sekolahList[position]

        nama_sekolah.text = "Nama : " + sekolah.nama_sekolah
        //gambar_sekolah.text = "Gambar : " + sekolah.gambar_sekolah
        keterangan.text = "Keterangan : " + sekolah.keterangan
        x.text = "x : "+ sekolah.x
        y.text = "y : "+sekolah.y
        return view
    }
}