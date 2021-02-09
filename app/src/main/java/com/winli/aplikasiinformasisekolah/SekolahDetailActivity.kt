package com.winli.aplikasiinformasisekolah

import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SekolahDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sekolah_detail)
        val extras = intent.extras

        val id_sekolah = extras!!.getString("id_sekolah")
        val nama_sekolah = extras!!.getString("nama_sekolah")
        val gambar_sekolah = extras!!.getString("gambar_sekolah")
        val keterangan = extras!!.getString("keterangan")
        val x = extras!!.getString("x")
        val y = extras!!.getString("y")

        val img_sekolah_detail = findViewById<ImageView>(R.id.img_sekolah_detail)

        var storageReference = FirebaseStorage.getInstance().getReference().child("sekolah/"+gambar_sekolah)
        var localFile = File.createTempFile(gambar_sekolah, "jpeg")
        storageReference.getFile(localFile)
            .addOnSuccessListener { taskSnapshot ->
                var bitmap: Bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                img_sekolah_detail.setImageBitmap(bitmap)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }

        val txt_judul_sekolah_detail = findViewById<TextView>(R.id.txt_judul_sekolah_detail)
        val txt_isi_sekolah_detail = findViewById<TextView>(R.id.txt_isi_sekolah_detail)

        txt_judul_sekolah_detail.text = nama_sekolah
        txt_isi_sekolah_detail.text = keterangan
    }
}