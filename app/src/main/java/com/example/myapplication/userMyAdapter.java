package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class userMyAdapter extends FirebaseRecyclerAdapter<dataholder,userMyAdapter.myviewholder> {

    public userMyAdapter(@NonNull FirebaseRecyclerOptions<dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final dataholder model) {
        holder.name.setText(model.getName());
        holder.quantity.setText(model.getQuantity());
        holder.price.setText(model.getPrice());
        holder.category.setText(model.getCategory());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,quantity,price,category;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.txtName);
            quantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            price = (TextView) itemView.findViewById(R.id.txtPrice);
            category = (TextView) itemView.findViewById(R.id.txtCategory);

        }
    }
}
