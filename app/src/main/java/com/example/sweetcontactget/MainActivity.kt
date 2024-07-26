package com.example.sweetcontactget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.sweetcontactget.data.Contact
import com.example.sweetcontactget.data.DataObject.addSweetieInfo
import com.example.sweetcontactget.fragments.contact.AllContactFragment
import com.example.sweetcontactget.fragments.contact.ContactFragment
import com.example.sweetcontactget.fragments.MyPageFragment
import com.example.sweetcontactget.fragments.randomCall.RandomFragment
import com.example.sweetcontactget.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        initNavigationBar()
        setFragment(AllContactFragment())
        requestPermission()

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
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        return super.dispatchTouchEvent(ev)
    }

    // Load Device Contacts
    @SuppressLint("Range")
    private fun getContactList() {
        val uri: Uri = ContactsContract.Data.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Relation.NAME,
            ContactsContract.CommonDataKinds.Note.NOTE,
            ContactsContract.Contacts.STARRED,
            ContactsContract.CommonDataKinds.Photo.PHOTO_URI
        )
        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        val hashMap = hashMapOf<String, Contact.SweetieInfo>()

        if (cursor != null) {
            Log.d("Contact", cursor.count.toString())
            while (cursor.moveToNext()) {
                val mimeType =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE))
                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                val sweetie = hashMap[id] ?: Contact.SweetieInfo(
                    null, "", "", "", "", 0, false
                )

                when (mimeType) {
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> sweetie.number =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                    ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE -> sweetie.relationship =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.NAME))

                    ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE -> sweetie.memo =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))

                    ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE -> {
                        val photo =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                        sweetie.imgSrc = photo?.let {
                            try {
                                val inputStream = contentResolver.openInputStream(Uri.parse(it))
                                Drawable.createFromStream(inputStream, it)
                            } catch (e: Exception) {
                                null
                            }
                        }
                    }

                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE -> sweetie.name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                }
                sweetie.isMarked =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.STARRED)) > 0
                hashMap[id] = sweetie
            }
            cursor.close()
        }
        hashMap.map { addSweetieInfo(it.value) }
        Toast.makeText(this, "연락처 목록을 불러왔습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun requestPermission() {
        val status =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            )

        if (status != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                0
            )
        } else {
            getContactList()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED)
            getContactList()
    }
}