package com.codeit.rajat.vidbuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.codeit.rajat.utilities.RecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedsActivity extends AppCompatActivity {

    private Button logoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String userName;
    private DatabaseReference dbRef;
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


        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView.setHasFixedSize(true);

        String []items =getResources().getStringArray(R.array.nav_items);

        for(String item : items){
            arrayList.add(item);
        }

        adapter = new RecyclerAdapter(arrayList);

        recyclerView.setAdapter(adapter);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            }
        };
        /*logoutBtn = findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });*/

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Uri photoUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        userPhoto = findViewById(R.id.user_photo);
        textViewUserName = findViewById(R.id.user_name);
        textViewUserName.setText(userName);
        Picasso.with(getApplicationContext()).load(photoUri)
                .into(userPhoto);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }
}
