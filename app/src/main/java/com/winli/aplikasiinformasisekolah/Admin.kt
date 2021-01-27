package com.winli.aplikasiinformasisekolah

data class Admin(
    val id_admin: String,
    val username: String,
    val password: String
){
    constructor(): this("","","")
}