package com.winli.aplikasiinformasisekolah

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_staff_ubah_profil.view.*
import java.io.File
import java.util.*

class StaffUbahProfilFragment(val idStaff: String) : Fragment() {

    private lateinit var refStaff: DatabaseReference
    private lateinit var staffList: MutableList<Staff>
    private lateinit var refSekolah: DatabaseReference
    private lateinit var sekolahList: MutableList<Sekolah>
    private lateinit var edtNamaSekolah: EditText
    private lateinit var edtKeterangan: EditText
    private lateinit var edtKoordinatX: EditText
    private lateinit var edtKoordinatY: EditText
    private lateinit var imgGambar: ImageView
    private lateinit var idSekolah: String
    private lateinit var dataSekolah: Sekolah
    private lateinit var imageUri: Uri
    private lateinit var randomKey: String
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var changeGambar: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff_ubah_profil, container, false)
        edtNamaSekolah = view.findViewById(R.id.edt_ubah_nama_sekolah)
        edtKeterangan = view.findViewById(R.id.edt_ubah_keterangan)
        edtKoordinatX = view.findViewById(R.id.edt_ubah_koordinat_x)
        edtKoordinatY = view.findViewById(R.id.edt_ubah_koordinat_y)
        imgGambar = view.findViewById(R.id.img_ubah_gambar_sekolah)

        refStaff = FirebaseDatabase.getInstance().getReference("staff")
        staffList = mutableListOf()

        refStaff.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    staffList.clear()
                    for (s in snapshot.children) {
                        val staff = s.getValue(Staff::class.java)
                        if (staff != null) {
                            staffList.add(staff)
                        }
                    }

                    for (staff in staffList) {
                        if (staff.id_staff == idStaff) {
                            idSekolah = staff.id_sekolah
                        }
                    }

                    refSekolah = FirebaseDatabase.getInstance().getReference("sekolah")
                    sekolahList = mutableListOf()

                    refSekolah.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()) {
                                sekolahList.clear()
                                for (s in snapshot.children) {
                                    val sekolah = s.getValue(Sekolah::class.java)
                                    if (sekolah != null) {
                                        sekolahList.add(sekolah)
                                    }
                                }

                                for (sekolah in sekolahList) {
                                    if(sekolah.id_sekolah == idSekolah) {
                                        dataSekolah = sekolah
                                    }
                                }

                                initData()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        view.btn_ubah_gambar_sekolah.setOnClickListener {
            pilihGambar()
        }

        view.btn_ubah_profil.setOnClickListener {
            uploadGambar()
        }

        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        return view
    }

    private fun initData() {
        edtNamaSekolah.setText(dataSekolah.nama_sekolah)
        edtKeterangan.setText(dataSekolah.keterangan)
        edtKoordinatX.setText(dataSekolah.x)
        edtKoordinatY.setText(dataSekolah.y)

        var storageReference = FirebaseStorage.getInstance().getReference().child("sekolah/"+dataSekolah.gambar_sekolah)
        var localFile = File.createTempFile(dataSekolah.gambar_sekolah, "jpeg")
        storageReference.getFile(localFile)
            .addOnSuccessListener { taskSnapshot ->
                var bitmap: Bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath())
                imgGambar.setImageBitmap(bitmap)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
            }

    }

    private fun pilihGambar() {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            imageUri = data.getData()!!
            imgGambar.setImageURI(imageUri)
            changeGambar = true
        }
    }

    private fun uploadGambar() {
        if(!changeGambar) {
            var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")

            val nama_sekolah = edtNamaSekolah.text.toString().trim()
            val keterangan = edtKeterangan.text.toString()
            val koordinatX = edtKoordinatX.text.toString()
            val koordinatY = edtKoordinatY.text.toString()

            if (nama_sekolah.isEmpty() or keterangan.isEmpty() or koordinatX.isEmpty() or koordinatY.isEmpty()) {
                Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                    .show()
                return
            }

            val sekolah = Sekolah(idSekolah, nama_sekolah, dataSekolah.gambar_sekolah, keterangan, koordinatX, koordinatY)
            ref.child(idSekolah).setValue(sekolah).addOnCompleteListener {
                Toast.makeText(context, "Data berhasil diupdate", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                    .show()
            }
            return
        }

        val gambar = imgGambar.drawable == null
        if(gambar) {
            Toast.makeText(context, "Gambar belum dipilih", Toast.LENGTH_SHORT).show()
            return
        }
        val pd = ProgressDialog(context)
        pd.setTitle("Upload Gambar")
        pd.show()

        randomKey = UUID.randomUUID().toString()
        val imageRef: StorageReference = storageReference.child("sekolah/" + randomKey)

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot -> // Get a URL to the uploaded content
                pd.dismiss()
                Toast.makeText(context, "Gambar berhasil diupload", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                    .show()
                hapusGambarLama()
                update()
            }
            .addOnFailureListener {
                pd.dismiss()
                Toast.makeText(context, "Gambar gagal diupload", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                    .show()
            }
            .addOnProgressListener { taskSnapshot ->
                val persen = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                pd.setMessage("Progress : " + persen.toInt() + "%")
            }
    }

    private fun hapusGambarLama() {
        var storageReference = FirebaseStorage.getInstance().getReference().child("sekolah/"+dataSekolah.gambar_sekolah)
        storageReference.delete()
    }

    private fun update(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")

        val nama_sekolah = edtNamaSekolah.text.toString().trim()
        val keterangan = edtKeterangan.text.toString()
        val koordinatX = edtKoordinatX.text.toString()
        val koordinatY = edtKoordinatY.text.toString()

        if (nama_sekolah.isEmpty() or keterangan.isEmpty() or koordinatX.isEmpty() or koordinatY.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val sekolah = Sekolah(idSekolah, nama_sekolah, randomKey, keterangan, koordinatX, koordinatY)
        ref.child(idSekolah).setValue(sekolah).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil diupdate", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}