package com.example.sweetcontactget.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sweetcontactget.Data.DataObject.contactMap

class BookmarkRepository {
    private val _bookmarkItems = mutableMapOf<Int, Contact.SweetieInfo>()
    val bookmarkItems: Map<Int, Contact.SweetieInfo> get() = _bookmarkItems.toMap()

    fun addToBookmark(id: Int) {
        if (contactMap.containsKey(id) && !_bookmarkItems.containsKey(id)) {
            _bookmarkItems[id] = contactMap[id] as Contact.SweetieInfo
        }
    }

    fun removeFromBookmark(id: Int) {
        _bookmarkItems.remove(id)
    }
}

class BookmarkViewModel(private val repository: BookmarkRepository = BookmarkRepository()) : ViewModel() {
    private val _bookmarkList: MutableLiveData<Map<Int, Contact.SweetieInfo>> = MutableLiveData()
    val bookmarkList: LiveData<Map<Int, Contact.SweetieInfo>> = _bookmarkList

    init {
        loadBookmarks()
    }

    private fun loadBookmarks() {
        _bookmarkList.value = repository.bookmarkItems
    }

    fun addToBookmark(id: Int) {
        repository.addToBookmark(id)
        _bookmarkList.value = repository.bookmarkItems
    }

    fun removeFromBookmark(id: Int) {
        repository.removeFromBookmark(id)
        _bookmarkList.value = repository.bookmarkItems
    }
}
