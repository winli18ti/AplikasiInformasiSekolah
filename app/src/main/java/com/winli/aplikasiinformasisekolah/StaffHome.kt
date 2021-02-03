package com.winli.aplikasiinformasisekolah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.staff_home.*

class StaffHome: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var staffBerita: StaffBeritaInputFragment
    lateinit var staffListBerita: StaffBeritaFragment
    lateinit var staffProfil: StaffUbahProfilFragment
    lateinit var idStaff: String
    //lateinit var guestBerita: GuestBeritaFragment
    //tambahkan fragment dari menu di sini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.staff_home)

        toggle = ActionBarDrawerToggle(this, staff_drawer, R.string.buka, R.string.tutup)
        staff_drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        staff_nav.setNavigationItemSelectedListener(this)

        idStaff = getIntent().getExtras()?.getString("idStaff").toString()

        //halaman pertama
        staffBerita = StaffBeritaInputFragment(idStaff)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.staff_layout, staffBerita)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.in_berita -> {
                staffBerita = StaffBeritaInputFragment(idStaff)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.staff_layout, staffBerita)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.staff_list_berita -> {
                staffListBerita = StaffBeritaFragment(idStaff)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.staff_layout, staffListBerita)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.edt_profil -> {
                staffProfil = StaffUbahProfilFragment(idStaff)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.staff_layout, staffProfil)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.logout -> {
                this.finish()
            }
        }
        staff_drawer.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}