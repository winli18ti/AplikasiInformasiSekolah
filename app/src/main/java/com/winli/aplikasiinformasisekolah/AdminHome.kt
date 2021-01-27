package com.winli.aplikasiinformasisekolah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.admin_home.*

class AdminHome: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var adminSekolah: AdminTambahSekolahFragment
    lateinit var adminListSekolah: AdminSekolahFragment
    lateinit var adminStaff: AdminTambahStaffFragment
    lateinit var adminListStaff: AdminStaffFragment
    lateinit var adminAdmin: AdminTambahAdminFragment
    lateinit var adminListAdmin: AdminAdminFragment
    //lateinit var guestBerita: GuestBeritaFragment
    //tambahkan fragment dari menu di sini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_home)

        toggle = ActionBarDrawerToggle(this, admin_drawer, R.string.buka, R.string.tutup)
        admin_drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        admin_nav.setNavigationItemSelectedListener(this)

        //halaman pertama
        adminSekolah = AdminTambahSekolahFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.admin_layout, adminSekolah)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.admin_sekolah -> {
                adminSekolah = AdminTambahSekolahFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_layout, adminSekolah)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.admin_list_sekolah -> {
                adminListSekolah = AdminSekolahFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_layout, adminListSekolah)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.admin_staff -> {
                adminStaff = AdminTambahStaffFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_layout, adminStaff)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.admin_list_staff -> {
                adminListStaff = AdminStaffFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_layout, adminListStaff)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.admin_admin -> {
                adminAdmin = AdminTambahAdminFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_layout, adminAdmin)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.admin_list_admin -> {
                adminListAdmin = AdminAdminFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.admin_layout, adminListAdmin)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.logout -> {
                this.finish()
            }
        }
        admin_drawer.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}