package com.e.dashadmin.ui.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dashadmin.R;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    Context context;
    List<Users> usersList;

    public myAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public myAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users, parent, false);

        return new  myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.myViewHolder holder, int position) {

        final Users users = usersList.get(position);

        final String FirstName = String.valueOf(users.getFirstName());
        final String SecondName = String.valueOf(users.getSecondName());
        final String Email = String.valueOf(users.getEmail());
        final String PhoneNumber = String.valueOf(users.getPhoneNumber());

        holder.FirstName.setText(FirstName);
        holder.SecondName.setText(SecondName);
        holder.Email.setText(Email);
        holder.phoneNumber.setText(PhoneNumber);

        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "User deleted!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView FirstName, SecondName, phoneNumber, Email;
        Button deleteUser;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            FirstName = itemView.findViewById(R.id.tvFirstName);
            SecondName = itemView.findViewById(R.id.tvSecondName);
            phoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            Email = itemView.findViewById(R.id.tvEmail);

            deleteUser = itemView.findViewById(R.id.btnDeleteUser);

        }
    }

}
