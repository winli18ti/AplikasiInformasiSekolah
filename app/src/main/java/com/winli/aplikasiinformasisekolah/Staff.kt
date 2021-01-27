package com.winli.aplikasiinformasisekolah

data class Staff(
    val id_staff: String,
    val nama_staff: String,
    val username: String,
    val password: String,
    val id_sekolah: String
){
    constructor(): this("","","","","")
}