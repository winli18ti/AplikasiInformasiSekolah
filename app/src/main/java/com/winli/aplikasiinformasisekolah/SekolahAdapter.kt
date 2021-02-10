package com.winli.aplikasiinformasisekolah

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SekolahAdapter(
    val sekolahContext: Context,
    val layoutResId: Int,
    val sekolahList: List<Sekolah>,
    val level: String
): ArrayAdapter<Sekolah>(sekolahContext, layoutResId, sekolahList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(sekolahContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama_sekolah: TextView = view.findViewById(R.id.txt_nama_sekolah)
        val gambar_sekolah: ImageView = view.findViewById(R.id.img_sekolah)
        val keterangan: TextView = view.findViewById(R.id.txt_isi_sekolah)
        val x: TextView = view.findViewById(R.id.txt_isi_x)
        val y: TextView = view.findViewById(R.id.txt_isi_y)

        val sekolah = sekolahList[position]

        nama_sekolah.text = sekolah.nama_sekolah
        keterangan.text = sekolah.keterangan

        var storageReference = FirebaseStorage.getInstance().getReference().child("sekolah/"+sekolah.gambar_sekolah)
        var localFile = File.createTempFile(sekolah.gambar_sekolah, "jpeg")
        storageReference.getFile(localFile)
            .addOnSuccessListener { taskSnapshot ->
                var bitmap: Bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                gambar_sekolah.setImageBitmap(bitmap)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
            }

        if (level.equals("Guest")) {
            view.setOnClickListener {
                val i = Intent(context, SekolahDetailActivity::class.java)
                i.putExtra("id_sekolah", sekolah.id_sekolah)
                i.putExtra("nama_sekolah", sekolah.nama_sekolah)
                i.putExtra("gambar_sekolah", sekolah.gambar_sekolah)
                i.putExtra("keterangan", sekolah.keterangan)
                i.putExtra("x", sekolah.x)
                i.putExtra("y", sekolah.y)
                context.startActivity(i)
            }
        }
        else {
            x.text = "Latitude : " + sekolah.x
            y.text = "Longitude : " + sekolah.y
        }

        return view
    }
}