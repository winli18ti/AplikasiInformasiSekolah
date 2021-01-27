package com.winli.aplikasiinformasisekolah

data class Sekolah (
    val id_sekolah: String,
    val nama_sekolah: String,
    val gambar_sekolah: String,
    val keterangan: String,
    val x: String,
    val y: String
){
    constructor(): this("","","","","","")
}