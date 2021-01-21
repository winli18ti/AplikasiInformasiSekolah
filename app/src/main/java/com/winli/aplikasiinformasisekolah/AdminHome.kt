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
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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