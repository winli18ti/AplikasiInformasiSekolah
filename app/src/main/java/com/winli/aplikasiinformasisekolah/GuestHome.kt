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
import kotlinx.android.synthetic.main.guest_home.*

class GuestHome: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var guestBerita: GuestBeritaFragment
    lateinit var guestSekolah: GuestSekolahFragment
    lateinit var tentangKami: TentangKamiFragment
    //tambahkan fragment dari menu di sini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guest_home)

        toggle = ActionBarDrawerToggle(this, guest_drawer, R.string.buka, R.string.tutup)
        guest_drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        guest_nav.setNavigationItemSelectedListener(this)
        //halaman pertama
        guestBerita = GuestBeritaFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.guest_layout, guestBerita)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.berita -> {
                guestBerita = GuestBeritaFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.guest_layout, guestBerita)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.cari_sekolah -> {
                guestSekolah = GuestSekolahFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.guest_layout, guestSekolah)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.tentang_kami -> {
                tentangKami = TentangKamiFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.guest_layout, tentangKami)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.logout -> {
                this.finish()
            }
        }
        guest_drawer.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}