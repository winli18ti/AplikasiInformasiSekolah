package com.winli.aplikasiinformasisekolah

data class Berita(
    val id_berita: String,
    val judul_berita: String,
    val isi_berita: String,
    val waktu_berita: String,
    val gambar_berita: String
){
    constructor(): this("","", "","","")
}