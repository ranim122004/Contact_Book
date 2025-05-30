package com.example.contact_book
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: ContactAdapter
    private var editingContactId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        val nameInput = findViewById<EditText>(R.id.etName)
        val phoneInput = findViewById<EditText>(R.id.etPhone)
        val addButton = findViewById<Button>(R.id.btnAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ContactAdapter(this, dbHelper.getAllContacts(),
            onDeleteClick = {
                dbHelper.deleteContact(it)
                refreshContacts()
            },
            onEditClick = { contact ->
                nameInput.setText(contact.name)
                phoneInput.setText(contact.phone)
                editingContactId = contact.id
                addButton.text = "Update Contact"
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        addButton.setOnClickListener {
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (editingContactId == null) {
                    dbHelper.insertContact(Contact(name = name, phone = phone))
                } else {
                    dbHelper.updateContact(Contact(id = editingContactId!!, name = name, phone = phone))
                    editingContactId = null
                    addButton.text = "Add Contact"
                }
                nameInput.text.clear()
                phoneInput.text.clear()
                refreshContacts()
            }
        }

    }

    private fun refreshContacts() {
        adapter.updateData(dbHelper.getAllContacts())
    }
}
