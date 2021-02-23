package com.nency.contact.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nency.contact.R;
import com.nency.contact.adapter.ContactAdapter;
import com.nency.contact.detail.ContactDetailActivity;
import com.nency.contact.room.Contact;
import com.nency.contact.room.ContactRoomDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Contact> contacts = new ArrayList();

    private ContactRoomDatabase contactRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new ContactAdapter(contacts, this);
        recyclerView.setAdapter(myAdapter);

        Button addNew = findViewById(R.id.addNew);
        addNew.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactDetailActivity.class);
                startActivity(i);
            }
        });

        // Room db
        contactRoomDatabase = contactRoomDatabase.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void loadContacts() {
        contacts.clear();
        contacts.addAll(contactRoomDatabase.ContactDao().getAllContact());
    }

    @Override
    public void onItemClick(int id) {
        Intent i = new Intent(this, ContactDetailActivity.class);
        i.putExtra("ContactId", id);
        startActivity(i);
    }
}





