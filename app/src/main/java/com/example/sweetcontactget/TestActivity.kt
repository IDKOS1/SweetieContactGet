package com.example.sweetcontactget

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentTransaction
import com.example.sweetcontactget.Fragments.Contact.AllContactFragment
import com.example.sweetcontactget.Fragments.Contact.ContactFragment
import com.example.sweetcontactget.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragment = ContactFragment()
        supportFragmentManager.beginTransaction().add(R.id.test,fragment).commit()

    }
}