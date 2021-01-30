package com.winli.aplikasiinformasisekolah

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_staff_berita_input.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class StaffBeritaInputFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etIsi: EditText
    private lateinit var imgGambarBerita: ImageView
    private lateinit var imageUri: Uri
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var randomKey: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_staff_berita_input, container, false)

        etName = view.findViewById(R.id.et_name)
        etIsi = view.findViewById(R.id.et_isi)
        imgGambarBerita = view.findViewById(R.id.img_gambar_berita)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        view.btn_upload_gambar_berita.setOnClickListener {
            pilihGambar()
        }

        view.btn_tambah_berita.setOnClickListener{
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
            imgGambarBerita.setImageURI(imageUri)
        }
    }

    private fun uploadGambar() {
        val gambar = imgGambarBerita.drawable == null
        if(gambar) {
            Toast.makeText(context, "Gambar belum dipilih", Toast.LENGTH_SHORT).show()
            return
        }
        val pd = ProgressDialog(context)
        pd.setTitle("Upload Gambar")
        pd.show()

        randomKey = UUID.randomUUID().toString()
        val imageRef: StorageReference = storageReference.child("berita/" + randomKey)

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
        var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("berita")

        val nama_berita = etName.text.toString().trim()
        val isi = etIsi.text.toString()

        if (nama_berita.isEmpty() or isi.isEmpty()) {
            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
            return
        }

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)

        val id_berita = ref.push().key
        val berita = Berita(id_berita!!, nama_berita, isi, formatted, randomKey)
        ref.child(id_berita).setValue(berita).addOnCompleteListener {
            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT) //getActivity() atau getApplicationContext()
                .show()
        }
    }
}