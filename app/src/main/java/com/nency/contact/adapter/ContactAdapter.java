package com.nency.contact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nency.contact.R;
import com.nency.contact.room.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;

    public ContactAdapter (Context context, ArrayList<Contact> list) {
        contacts = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            number = itemView.findViewById(R.id.number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contacts, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(contacts.get(position));
        holder.firstName.setText(position);
        holder.lastName.setText(position);
        holder.number.setText(position);

    }

    @Override
    public int getItemCount() {
        if (contacts == null) {
            return 0;
        } else {
            return contacts.size();
        }
    }
}










