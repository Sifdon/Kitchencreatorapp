package com.kitchencreator.kitchencreator.Dashboard;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kitchencreator.kitchencreator.Accountactivity.LoginActivity;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Database.Database;
import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.ViewHolder.OrderViewHolder;
import com.kitchencreator.kitchencreator.model.Requests;

public class Yourorders extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase fd;
    DatabaseReference ad;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseRecyclerAdapter<Requests,OrderViewHolder> adapter;

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourorders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Your Orders");
        setSupportActionBar(toolbar);

        fd = FirebaseDatabase.getInstance();
        ad = fd.getReference("Requests");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        recyclerView = findViewById(R.id.orderrecylerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadorders(user.getUid());
    }

    private void loadorders(String uid) {
        adapter = new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(Requests.class,
                R.layout.orderlayout,OrderViewHolder.class,ad.orderByChild("id").equalTo(uid)) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Requests model, int position) {
                viewHolder.orderphone.setText(model.getPhoneno());
                viewHolder.ordername.setText(model.getName());
                viewHolder.orderid.setText(adapter.getRef(position).getKey());
                viewHolder.orderaddress.setText(model.getAddress());
                viewHolder.orderstatus.setText(Common.converttostatus(model.getStatus()));
                viewHolder.ordertotal.setText(model.getTotal());

              viewHolder.setItemClickListener(new ItemClickListener() {
                  @Override
                  public void onClick(View view, int position, boolean isLongClick) {
                      Intent orderdeta = new Intent(Yourorders.this,Orderdetails.class);
                      Common.currentrequest = model;
                      orderdeta.putExtra("Orderid",adapter.getRef(position).getKey());
                      startActivity(orderdeta);
                  }
              });


            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {
            case R.id.category:
                finish();
                startActivity(new Intent(Yourorders.this, Showcategories.class));
                break;
            case R.id.order:
                finish();
                startActivity(new Intent(Yourorders.this, Yourorders.class));
                break;
            case R.id.cart:
                finish();
                startActivity(new Intent(Yourorders.this, showcart.class
                ));
                break;
            case R.id.setting:
                finish();
                startActivity(new Intent(Yourorders.this, Settings.class));
                break;
            case R.id.about:
                break;
            case R.id.signout:
                auth.signOut();
                finish();
                startActivity(new Intent(Yourorders.this, LoginActivity.class));
                break;
            default:
                startActivity(new Intent(Yourorders.this,Showcategories.class));
                break;
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainroom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            int id = item.getItemId();
            switch (id){
                case R.id.signout:
                    auth.signOut();

                    startActivity(new Intent(this, LoginActivity.class));
                    break;
            }
        }
        catch (Exception e)
        {
            Log.e("Error",""+e);
        }

        return super.onOptionsItemSelected(item);
    }




}