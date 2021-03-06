package com.winli.aplikasiinformasisekolah

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_admin_tambah_sekolah.view.*
import java.util.*


class AdminTambahSekolahFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etKeterangan: EditText
    private lateinit var etKoordinatx: EditText
    private lateinit var etKoordinaty: EditText
    private lateinit var imgGambarSekolah: ImageView
    private lateinit var imageUri: Uri
    private lateinit var randomKey: String
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_tambah_sekolah, container, false)

        etName = view.findViewById(R.id.et_name)
        etKeterangan = view.findViewById(R.id.et_keterangan)
        etKoordinatx = view.findViewById(R.id.et_koordinat_x)
        etKoordinaty = view.findViewById(R.id.et_koordinat_y)
        imgGambarSekolah = view.findViewById(R.id.img_gambar_sekolah)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        view.btn_upload_gambar_sekolah.setOnClickListener{
            pilihGambar()
        }

        view.btn_tambah_sekolah.setOnClickListener{
            uploadGambar()
        }
        return view
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
            imgGambarSekolah.setImageURI(imageUri)
        }
    }

    private fun uploadGambar() {
        val gambar = imgGambarSekolah.drawable == null
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
                insert()
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

    private fun insert(){
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("sekolah")

        val nama_sekolah = etName.text.toString().trim()
        val keterangan = etKeterangan.text.toString()
        val koordinatX = etKoordinatx.text.toString()
        val koordinatY = etKoordinaty.text.toString()

        if (nama_sekolah.isEmpty() or keterangan.isEmpty() or koordinatX.isEmpty() or koordinatY.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val id_sekolah = ref.push().key
        val sekolah = Sekolah(id_sekolah!!, nama_sekolah, randomKey, keterangan, koordinatX, koordinatY)
        ref.child(id_sekolah).setValue(sekolah).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}