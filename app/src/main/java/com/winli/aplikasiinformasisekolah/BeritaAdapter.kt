package com.winli.aplikasiinformasisekolah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class BeritaAdapter(
    val beritaContext: Context,
    val layoutResId: Int,
    val beritaList: List<Berita>
): ArrayAdapter<Berita>(beritaContext, layoutResId, beritaList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(beritaContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val judul_berita: TextView = view.findViewById(R.id.txt_judul_berita)
        val isi_berita: TextView = view.findViewById(R.id.txt_isi_berita)
        //val gambar_berita: TextView = view.findViewById(R.id.img_berita)
        val waktu_berita: TextView = view.findViewById(R.id.txt_waktu_berita)

        val berita = beritaList[position]

        judul_berita.text = "Judul : " + berita.judul_berita
        isi_berita.text = "Isi : " + berita.isi_berita
        //gambar_berita.text = "Gambar : " + berita.gambar_berita
        waktu_berita.text = "Waktu : " + berita.waktu_berita

        return view
    }
}