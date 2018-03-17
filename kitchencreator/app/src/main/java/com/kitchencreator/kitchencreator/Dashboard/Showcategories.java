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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kitchencreator.kitchencreator.Accountactivity.LoginActivity;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Database.Database;
import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.Service.ListenOrder;
import com.kitchencreator.kitchencreator.ViewHolder.MenuViewHolder;
import com.kitchencreator.kitchencreator.model.Category;
import com.kitchencreator.kitchencreator.model.commondetails;
import com.squareup.picasso.Picasso;

public class Showcategories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseDatabase mainfdb;
    private DatabaseReference category,userdetails,ref;
    private TextView txtFullname;
    private FirebaseAuth proauth;
    private RecyclerView recyclemenu;
    private NavigationView navigationView;

    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
try {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //init firebase
        proauth = FirebaseAuth.getInstance();
        mainfdb = FirebaseDatabase.getInstance();
        category = mainfdb.getReference("category");
        userdetails = mainfdb.getReference("users");
        ref = mainfdb.getReference("carts");


        userdetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               assert  proauth.getUid() != null;
                commondetails details = dataSnapshot.child(proauth.getUid()).getValue(commondetails.class);

                assert details != null;
                if (details.getFullname() !=null){
                    Common.userdetail = details;
                    showname();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //textview display username



        //load menu
        recyclemenu = findViewById(R.id.recyclermenu);

        layoutManager = new LinearLayoutManager(this);
        recyclemenu.setLayoutManager(layoutManager);

    startService(new Intent(this, ListenOrder.class));

        loadmenu();
       // oldcart();
}
catch (Exception e)
{
    Log.e("Error",""+e);
}

    }

  /*  private void oldcart() {
        try {


        FirebaseUser ccuser = proauth.getCurrentUser();

        assert ccuser != null;

        ref.child(ccuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("cart")) {
                    Cart cartp = dataSnapshot.getValue(Cart.class);

                    assert cartp != null;
                    for (Order order : cartp.getCart())
                        new Database(Showcategories.this).addtocart(order);
                }




            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    catch (Exception e)
    {
        Log.e("Error",""+e);
    }
    }*/

    private void showname() {
        try {


            View headerView = navigationView.getHeaderView(0);
            txtFullname = headerView.findViewById(R.id.txtfullname);
            txtFullname.setText(Common.userdetail.getFullname());
        }
        catch (Exception e)
        {
            Log.e("Error",""+e);
        }
    }


    private void loadmenu() {
try {


        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtmenuname.setText(model.getName());
                //viewHolder.menudescription.setText(model.getDescription());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.menuimage);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent product = new Intent(Showcategories.this,productlist.class) ;
                        product.putExtra("Categoryid",adapter.getRef(position).getKey());
                        startActivity(product);
                    }
                });

            }
        };

        recyclemenu.setAdapter(adapter);
    }
catch (Exception e)
{
    Log.e("Error",""+e);
}
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
            new Database(this).cleancart();
        startActivity(new Intent(Showcategories.this, LoginActivity.class));
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
        try {
            int id = item.getItemId();


            switch (id) {
                case R.id.category:
                    finish();
                    startActivity(new Intent(Showcategories.this, Showcategories.class));
                    break;
                case R.id.order:
                    startActivity(new Intent(Showcategories.this, Yourorders.class));
                    break;
                case R.id.cart:
                    finish();
                    startActivity(new Intent(Showcategories.this, showcart.class
                    ));
                    break;
                case R.id.setting:
                    finish();
                    startActivity(new Intent(Showcategories.this, Settings.class));
                    break;
                case R.id.about:

                    break;

                default:
                    startActivity(new Intent(Showcategories.this, Popularproducts.class));
                    break;
            }


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

catch (Exception e)
    {
        Log.e("Error",""+e);
    }
        return true;
    }


    public void showcart(View view) {
        startActivity(new Intent(this,showcart.class));
    }
}


