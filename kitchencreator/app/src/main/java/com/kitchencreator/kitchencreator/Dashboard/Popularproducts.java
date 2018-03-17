package com.kitchencreator.kitchencreator.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kitchencreator.kitchencreator.Accountactivity.LoginActivity;
import com.kitchencreator.kitchencreator.R;

import com.kitchencreator.kitchencreator.ViewHolder.MenuViewHolder;
import com.kitchencreator.kitchencreator.model.Category;


public class Popularproducts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase mainfdb;
    private DatabaseReference category,userdetails;
    private TextView txtFullname;
    private FirebaseAuth proauth;
    private RecyclerView recyclemenu;
    private NavigationView navigationView;

    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popularproducts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);


                //init firebase
                proauth = FirebaseAuth.getInstance();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //textview display username



        //load menu





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
                    proauth.signOut();

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {
            case R.id.category:
                finish();
                startActivity(new Intent(Popularproducts.this, Showcategories.class));
                break;
            case R.id.order:
                startActivity(new Intent(Popularproducts.this, Yourorders.class));
                break;
            case R.id.cart:
                finish();
                startActivity(new Intent(Popularproducts.this, showcart.class
                ));
                break;
            case R.id.setting:
                finish();
                startActivity(new Intent(Popularproducts.this, Settings.class));
                break;
            case R.id.about:

                break;
           /* case R.id.signout:
                proauth.signOut();
                startActivity(new Intent(Popularproducts.this, LoginActivity.class));
                break;*/
            default:
                startActivity(new Intent(Popularproducts.this,Showcategories.class));
                break;
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
