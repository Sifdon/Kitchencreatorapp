package com.kitchencreator.kitchencreator.Dashboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.kitchencreator.kitchencreator.Accountactivity.LoginActivity;
import com.kitchencreator.kitchencreator.R;

public class Settings extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseAuth auth;

    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case R.id.category:
                finish();
                startActivity(new Intent(this, Showcategories.class));

                break;
            case R.id.order:
                finish();
                startActivity(new Intent(
                        this, Yourorders.class
                ));

                break;
            case R.id.cart:
                finish();
                startActivity(new Intent(
                        this, showcart.class
                ));

                break;
            case R.id.setting:
                startActivity(new Intent(
                        this, Settings.class
                ));
                break;
            case R.id.about:

                break;

            default:
                startActivity(new Intent(Settings.this,Showcategories.class));
                break;

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
