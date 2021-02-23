package com.nency.contact.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nency.contact.R;
import com.nency.contact.dashboard.MainActivity;
import com.nency.contact.dashboard.OnItemClickListener;
import com.nency.contact.room.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;
    private OnItemClickListener onItemClickListener;

    public ContactAdapter(ArrayList<Contact> list, @NonNull OnItemClickListener onItemClickListener) {
        contacts = list;
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int id;
        TextView firstName, number;
        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);

            number = itemView.findViewById(R.id.number);
            // remove item listener
            itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> onItemClickListener.onItemRemovedClicked(id));
            // contact click listener
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(id));
            //contact long press listener
            itemView.setOnLongClickListener(v -> {onItemClickListener.onItemLongClick(id); return true;});
        }
    }


    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contacts, parent, false);
        return new ViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.id = contact.getId();
        holder.firstName.setText(contact.getFirstName() + " " + contact.getLastName());
        holder.number.setText(contact.getPhoneNumber());
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










