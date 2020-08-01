package com.example.edugorillaintern.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edugorillaintern.MainActivity3;
import com.example.edugorillaintern.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {
    private Context context;
    private List<String> username,emails;

    public MyAdapter(Context context, List<String> username, List<String> emails) {
        this.context = context;
        this.username = username;
        this.emails = emails;
    }

    public MyAdapter(List<String> username, Context context) {
        this.context = context;
        this.username = username;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recyler_view,parent,false);
                return  new MyViewHolder(view,context);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String data = username.get(position);
        String data2 = emails.get(position);
        holder.userName.setText(data);
        holder.email.setText(data2);

    }

    @Override
    public int getItemCount() {
        return username.size() ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView userName ,email;


        public MyViewHolder(@NonNull View itemView ,Context ctx) {
            super(itemView);
            context = ctx;
        userName  = itemView.findViewById(R.id.recyler_view_userName);
        email= itemView.findViewById(R.id.recyler_view_email);

       // adding click listener in the particular tab of the recycler view

        itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MainActivity3.class);
            context.startActivity(intent);
        }
    }
   }
