package com.example.sweetcontactget

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sweetcontactget.Fragments.Contact.AllContactFragment
import com.example.sweetcontactget.Fragments.Contact.ContactFragment
import com.example.sweetcontactget.Fragments.MyPageFragment
import com.example.sweetcontactget.Fragments.RandomCall.RandomFragment
import com.example.sweetcontactget.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        initNavigationBar()
        setFragment(AllContactFragment())

        binding.bottomNavigationView.selectedItemId = R.id.contactsItem

    }

    private fun initNavigationBar() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.randomItem -> {
                        setFragment(RandomFragment())
                        true
                    }

                    R.id.contactsItem -> {
                        setFragment(ContactFragment())
                        true
                    }

                    R.id.myProfileItem -> {
                        setFragment(MyPageFragment())
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, fragment).commit()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        return super.dispatchTouchEvent(ev)
    }
}