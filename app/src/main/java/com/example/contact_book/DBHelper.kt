package com.example.contact_book
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "ContactDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Contact (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Contact")
        onCreate(db)
    }

    fun insertContact(contact: Contact): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", contact.name)
            put("phone", contact.phone)
        }
        return db.insert("Contact", null, values) != -1L
    }

    fun updateContact(contact: Contact): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", contact.name)
            put("phone", contact.phone)
        }
        return db.update("Contact", values, "id=?", arrayOf(contact.id.toString())) > 0
    }


    fun getAllContacts(): ArrayList<Contact> {
        val list = ArrayList<Contact>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM Contact", null)
        while (cursor.moveToNext()) {
            list.add(Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
        }
        cursor.close()
        return list
    }

    fun deleteContact(id: Int): Boolean {
        return writableDatabase.delete("Contact", "id=?", arrayOf(id.toString())) > 0
    }
}
