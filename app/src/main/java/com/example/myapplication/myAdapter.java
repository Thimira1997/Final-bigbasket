package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myAdapter extends FirebaseRecyclerAdapter<dataholder,myAdapter.myviewholder> {

    public myAdapter(@NonNull FirebaseRecyclerOptions<dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final dataholder model) {
        holder.name.setText(model.getName());
        holder.quantity.setText(model.getQuantity());
        holder.price.setText(model.getPrice());
        holder.category.setText(model.getCategory());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);

        holder.editproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogplus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true,1300)
                        .create();

                View myview = dialogplus.getHolderView();
                final EditText upname = myview.findViewById(R.id.upname);
                final EditText upquan = myview.findViewById(R.id.upquan);
                final EditText upprice = myview.findViewById(R.id.upprice);
                final EditText upcat = myview.findViewById(R.id.upcategory);
                Button upsubmit = myview.findViewById(R.id.usubmit);

                upname.setText(model.getName());
                upquan.setText(model.getQuantity());
                upprice.setText(model.getPrice());
                upcat.setText(model.getCategory());

                dialogplus.show();

                upsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",upname.getText().toString());
                        map.put("quantity",upquan.getText().toString());
                        map.put("price",upprice.getText().toString());
                        map.put("category",upcat.getText().toString());

                    

                        FirebaseDatabase.getInstance().getReference().child("products")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogplus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogplus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Product");
                builder.setMessage("Are you sure you want to Delete the Product..???");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("products")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,quantity,price,category;
        ImageView editproduct,deleteproduct;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.txtName);
            quantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            price = (TextView) itemView.findViewById(R.id.txtPrice);
            category = (TextView) itemView.findViewById(R.id.txtCategory);

            editproduct = (ImageView)itemView.findViewById(R.id.editicon);
            deleteproduct = (ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}

