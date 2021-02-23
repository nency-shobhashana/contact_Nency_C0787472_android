package com.nency.contact.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nency.contact.R;
import com.nency.contact.room.Contact;
import com.nency.contact.room.ContactRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailActivity extends AppCompatActivity {

    int contactId = -1;

    private ContactRoomDatabase contactRoomDatabase;

    EditText firstName, lastName, number, email, address;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        contactId = getIntent().getIntExtra("ContactId", -1);
        // Room db
        contactRoomDatabase = contactRoomDatabase.getInstance(this);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactId < 0) {
                    addContact();
                } else {
                    updateContact();
                }
            }
        });

        if (contactId >= 0) {
            Contact contact = contactRoomDatabase.ContactDao().getNote(contactId);
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            number.setText(contact.getPhoneNumber());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
        }
    }

    private void addContact() {
        String contactFirstName = firstName.getText().toString();
        String contactLastName = lastName.getText().toString();
        String contactNumber = number.getText().toString();
        String contactEmail = email.getText().toString();
        String contactAddress = address.getText().toString();

        if (contactFirstName.isEmpty()) {
            firstName.setError("Note title cannot be empty.");
            firstName.requestFocus();
            return;
        }
        if (contactLastName.isEmpty()) {
            lastName.setError("Note description cannot be empty");
            lastName.requestFocus();
            return;
        }
        // Insert note into room
        Contact contact = new Contact(contactFirstName, contactLastName, contactEmail, contactNumber, contactAddress);
        contactRoomDatabase.ContactDao().insertContact(contact);
        Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
        redirectAllContacts();

    }

    private void updateContact() {
        String contactFirstName = firstName.getText().toString();
        String contactLastName = lastName.getText().toString();
        String contactNumber = number.getText().toString();
        String contactEmail = email.getText().toString();
        String contactAddress = address.getText().toString();

        contactRoomDatabase.ContactDao().updateContact(contactId,contactFirstName, contactLastName, contactEmail, contactNumber, contactAddress);
        redirectAllContacts();
    }


    private void redirectAllContacts() {
        finish();
    }

}









