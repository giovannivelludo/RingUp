package com.gruppo4.ringUp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruppo4.ringUp.structure.Contact;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final int CONTACT = 0;
    private final int NEW_CONTACT = 1;
    private ArrayList<Contact> peerList;

    public ContactAdapter(ArrayList<Contact> myDataset) {
        peerList = myDataset;
    }

    /**
     * Checks whether the given position must be an add contact button or just a normal contact
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position < peerList.size()) {
            return CONTACT;
        } else {
            return NEW_CONTACT;
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        Log.v("ContactAdapter", "View type: " + viewType);
        if (viewType == NEW_CONTACT) {
            return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_contact, parent, false));
        } else if (viewType == CONTACT) {
            return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_name, parent, false));
        } else {
            return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.password_dialog, parent, false));
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Log.v("ContactAdapter", "onBindViewHolder position: " + position);
        if (position < peerList.size())
            holder.nameTextView.setText(peerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return peerList.size() + 1;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contactNameText);
        }

    }
}
