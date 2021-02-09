package com.winli.aplikasiinformasisekolah

import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class BeritaDetailActivity: AppCompatActivity() {
    private lateinit var staffList: MutableList<Staff>
    private lateinit var sekolahList: MutableList<Sekolah>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita_detail)
        val extras = intent.extras

        val id_berita = extras!!.getString("id_berita")
        val judul_berita = extras.getString("judul_berita")
        val gambar_berita = extras.getString("gambar_berita")
        val waktu_berita = extras.getString("waktu_berita")
        val isi_berita = extras.getString("isi_berita")
        val id_sekolah = extras.getString("id_sekolah")
        val id_staff = extras.getString("id_staff")

        val img_berita_detail = findViewById<ImageView>(R.id.img_berita_detail)

        var storageReference = FirebaseStorage.getInstance().getReference().child("berita/"+gambar_berita)
        var localFile = File.createTempFile(gambar_berita, "jpeg")
        storageReference.getFile(localFile)
            .addOnSuccessListener { taskSnapshot ->
                var bitmap: Bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                img_berita_detail.setImageBitmap(bitmap)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }

        val txt_judul_berita_detail = findViewById<TextView>(R.id.txt_judul_berita_detail)
        val txt_waktu_berita_detail = findViewById<TextView>(R.id.txt_waktu_berita_detail)
        val txt_nama_staff_berita_detail = findViewById<TextView>(R.id.txt_nama_staff_berita_detail)
        val txt_nama_sekolah_berita_detail = findViewById<TextView>(R.id.txt_nama_sekolah_berita_detail)
        val txt_isi_berita_detail = findViewById<TextView>(R.id.txt_isi_berita_detail)

        txt_judul_berita_detail.text = judul_berita
        txt_waktu_berita_detail.text = waktu_berita

        val refStaff: DatabaseReference = FirebaseDatabase.getInstance().getReference("staff")
        staffList = mutableListOf()
        refStaff.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    staffList.clear()
                    for (s in snapshot.children) {
                        val staff = s.getValue(Staff::class.java)
                        if (staff != null) {
                            staffList.add(staff)
                            //nama_staff = staff.nama_staff
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        var nama_staff = ""
        for (staff in staffList) {
            if (staff.id_staff.equals(id_staff)) {
                nama_staff = staff.nama_staff
            }
        }

        val refSekolah: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")
        sekolahList = mutableListOf()
        refSekolah.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    sekolahList.clear()
                    for (s in snapshot.children) {
                        val sekolah = s.getValue(Sekolah::class.java)
                        if (sekolah != null) {
                            sekolahList.add(sekolah)
                            //nama_sekolah = sekolah.nama_sekolah
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        var nama_sekolah = ""
        for (sekolah in sekolahList) {
            if (sekolah.id_sekolah.equals(id_sekolah)) {
                nama_sekolah = sekolah.nama_sekolah
            }
        }

        txt_nama_staff_berita_detail.text = nama_staff
        txt_nama_sekolah_berita_detail.text = nama_sekolah
        txt_isi_berita_detail.text = isi_berita
    }
}