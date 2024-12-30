package com.example.contac1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ContactDatabase.getDatabase(application)
    private val contactDao = database.contactDao()

    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts().asLiveData()

    fun insertContact(lastName: String, phoneNumber: String) {
        viewModelScope.launch {
            contactDao.insert(Contact(lastName = lastName, phoneNumber = phoneNumber))
        }
    }
}
