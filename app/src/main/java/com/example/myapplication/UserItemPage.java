package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class UserItemPage extends AppCompatActivity {

    RecyclerView recyclerView;
    userMyAdapter adapter;
    FloatingActionButton addCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addCart = findViewById(R.id.fabAddCart);


        FirebaseRecyclerOptions<dataholder> options =
                new FirebaseRecyclerOptions.Builder<dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), dataholder.class)
                        .build();

        adapter = new userMyAdapter(options);
        recyclerView.setAdapter(adapter);

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(UserItemPage.this, Addtocart.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.searchProduct);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {
        FirebaseRecyclerOptions<dataholder> options =
                new FirebaseRecyclerOptions.Builder<dataholder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("category")
                                .startAt(s).endAt(s+"\uf8ff"), dataholder.class)
                        .build();

        adapter = new userMyAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
