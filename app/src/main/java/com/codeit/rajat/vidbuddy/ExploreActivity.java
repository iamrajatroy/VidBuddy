package com.codeit.rajat.vidbuddy;

import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeit.rajat.utilities.RecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {
    private String userName;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ActionBarDrawerToggle drawerToggle;
    private ImageView userPhoto;
    private TextView textViewUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Explore");

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView.setHasFixedSize(true);



        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout
                ,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Uri photoUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        userPhoto = findViewById(R.id.user_photo);
        textViewUserName = findViewById(R.id.user_name);
        textViewUserName.setText(userName);
        Picasso.with(getApplicationContext()).load(photoUri)
                .into(userPhoto);

        String []items =getResources().getStringArray(R.array.nav_items);

        for(String item : items){
            arrayList.add(item);
        }

        adapter = new RecyclerAdapter(arrayList);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }
}
