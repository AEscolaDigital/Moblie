package com.example.school.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.school.R
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.school.adapter.DashboardAdapter
import com.example.school.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottom_navigation: BottomNavigationView

    private val teamsFragment = TeamsFragment()
    private val taskFragment = TaskFragment()
    private val callFragment = CallFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_fragments)


        // Remover a AppBar
        supportActionBar!!.hide()


        bottom_navigation = findViewById(R.id.navegation_bottom)
        //set bottom navigation default item selected
        bottom_navigation.selectedItemId = R.id.ic_home
        //set teamsFragment per default
        replaceFragment(teamsFragment)

        // Fazendo a troca de tela ao clicar em um icone no bottom_navigation
        bottom_navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    replaceFragment(teamsFragment)
                    true
                }
                R.id.ic_tarefas -> {
                    replaceFragment(taskFragment)
                    true
                }

                R.id.ic_chamada -> {
                    replaceFragment(callFragment)
                    true
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}

