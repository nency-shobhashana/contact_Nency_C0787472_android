package com.nency.contact.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    TextView txtTotalContacts;

    ArrayList<Contact> contacts = new ArrayList();
    ArrayList<Contact> filterContacts = new ArrayList();
    private ContactRoomDatabase contactRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        txtTotalContacts = findViewById(R.id.totalContact);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new ContactAdapter(filterContacts, this);
        recyclerView.setAdapter(myAdapter);

        Button addNew = findViewById(R.id.addNew);
        addNew.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ContactDetailActivity.class);
            startActivity(i);
        });

        // Room db
        contactRoomDatabase = ContactRoomDatabase.getInstance(this);

        // search view
        SearchView searchView = findViewById(R.id.searchContacts);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filter data based on search query text
                filterContacts.clear();
                for (Contact contact : contacts) {
                    if (contact.getFirstName().contains(newText)
                            || contact.getLastName().contains(newText)) {
                        filterContacts.add(contact);
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {
        contacts.clear();
        contacts.addAll(contactRoomDatabase.ContactDao().getAllContact());
        filterContacts.clear();
        filterContacts.addAll(contacts);
        recyclerView.getAdapter().notifyDataSetChanged();
        txtTotalContacts.setText("Total Contacts is " + contacts.size());
    }

    @Override
    public void onItemClick(int id) {
        Intent i = new Intent(this, ContactDetailActivity.class);
        i.putExtra("ContactId", id);
        startActivity(i);
    }

    // Remove contact
    @Override
    public void onItemRemovedClicked(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            contactRoomDatabase.ContactDao().deleteContact(id);
            loadContacts();
        });
        builder.setNegativeButton("No", null);
        builder.create().show();

    }


    // OnLong Press Action
    @Override
    public void onItemLongClick(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                selectOption(contact.getPhoneNumber(), contact.getEmail());
                break;
            }
        }
    }

    private void selectOption(String phone, String email) {
        ArrayList<CharSequence> options = new ArrayList<>();
        if (phone != null) {
            options.add("Call");
            options.add("Message");
        }
        if (email != null) {
            options.add("Email");
        }
        options.add("Cancel");
        CharSequence[] finalOption = new CharSequence[options.size()];
        options.toArray(finalOption);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");

        builder.setItems(finalOption, (dialog, item) -> {

            if (finalOption[item].equals("Call")) {
                String tel = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(tel));
                MainActivity.this.startActivity(intent);

            } else if (finalOption[item].equals("Message")) {
                String sms = "sms:" + phone;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(sms));
                MainActivity.this.startActivity(intent);

            } else if (finalOption[item].equals("Email")) {
                String mail = "mailto:" + email;
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(mail));
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                MainActivity.this.startActivity(intent);

            } else if (finalOption[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}





