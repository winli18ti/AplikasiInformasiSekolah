package com.winli.aplikasiinformasisekolah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class BeritaAdapter(
    val beritaContext: Context,
    val layoutResId: Int,
    val beritaList: List<Berita>,
    val level: String
): ArrayAdapter<Berita>(beritaContext, layoutResId, beritaList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(beritaContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val judul_berita: TextView = view.findViewById(R.id.txt_judul_berita)
        val isi_berita: TextView = view.findViewById(R.id.txt_isi_berita)
        val gambar_berita: ImageView = view.findViewById(R.id.img_berita)
        val waktu_berita: TextView = view.findViewById(R.id.txt_waktu_berita)

        val berita = beritaList[position]

        judul_berita.text = berita.judul_berita
        isi_berita.text = berita.isi_berita
        waktu_berita.text = berita.waktu_berita

        var storageReference = FirebaseStorage.getInstance().getReference().child("berita/"+berita.gambar_berita)
        var localFile = File.createTempFile(berita.gambar_berita, "jpeg")
        storageReference.getFile(localFile)
            .addOnSuccessListener { taskSnapshot ->
                var bitmap: Bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                gambar_berita.setImageBitmap(bitmap)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
            }

        if (level.equals("Guest")) {
            view.setOnClickListener {
                val i = Intent(context, BeritaDetailActivity::class.java)
                i.putExtra("id_berita", berita.id_berita)
                i.putExtra("judul_berita", berita.judul_berita)
                i.putExtra("gambar_berita", berita.gambar_berita)
                i.putExtra("waktu_berita", berita.waktu_berita)
                i.putExtra("isi_berita", berita.isi_berita)
                i.putExtra("id_sekolah", berita.id_sekolah)
                i.putExtra("id_staff", berita.id_staff)
                context.startActivity(i)
            }
        }

        return view
    }
}