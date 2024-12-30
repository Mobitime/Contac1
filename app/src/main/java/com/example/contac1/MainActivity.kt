package com.example.contac1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ContactViewModel
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var contactsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        lastNameEditText = findViewById(R.id.lastNameEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        saveButton = findViewById(R.id.saveButton)
        contactsTextView = findViewById(R.id.contactsTextView)

        saveButton.setOnClickListener {
            val lastName = lastNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()

            if (lastName.isNotBlank() && phoneNumber.isNotBlank()) {
                viewModel.insertContact(lastName, phoneNumber)
                lastNameEditText.text.clear()
                phoneNumberEditText.text.clear()
            }
        }

        viewModel.allContacts.observe(this) { contacts ->
            val contactsText = contacts.joinToString("\n") { 
                "Фамилия: ${it.lastName}, Телефон: ${it.phoneNumber}" 
            }
            contactsTextView.text = contactsText
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}