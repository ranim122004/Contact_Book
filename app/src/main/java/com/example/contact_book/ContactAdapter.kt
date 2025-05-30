package com.example.contact_book
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val context: Context,
    private var contacts: ArrayList<Contact>,
    private val onDeleteClick: (Int) -> Unit,
    private val onEditClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {


    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val phone: TextView = view.findViewById(R.id.tvPhone)
        val deleteBtn: ImageButton = view.findViewById(R.id.btnDelete)
        val editBtn: ImageButton = view.findViewById(R.id.btnEdit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name
        holder.phone.text = contact.phone

        holder.deleteBtn.setOnClickListener { onDeleteClick(contact.id) }
        holder.editBtn.setOnClickListener { onEditClick(contact) }
    }


    fun updateData(newContacts: ArrayList<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}
