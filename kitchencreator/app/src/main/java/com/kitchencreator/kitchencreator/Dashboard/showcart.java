package com.kitchencreator.kitchencreator.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.kitchencreator.kitchencreator.Accountactivity.LoginActivity;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Database.Database;
import com.kitchencreator.kitchencreator.Interface.ItemClickListener;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.ViewHolder.CartAdapter;
import com.kitchencreator.kitchencreator.ViewHolder.ShowCartViewHolder;
import com.kitchencreator.kitchencreator.model.Cart;
import com.kitchencreator.kitchencreator.model.Order;
import com.kitchencreator.kitchencreator.model.Showcart;
import com.kitchencreator.kitchencreator.placeorder.Placeorder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class showcart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclercart;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference requests;
    FirebaseAuth proauth;
    private DatabaseReference ref;
    FirebaseUser ccuser;
    Handler mHandler;

    private TextView txtprice;
    private List<Order> carts = new ArrayList<>();
    List<Order> Pro = new ArrayList<>();

    private CartAdapter adapter;
    private FirebaseRecyclerAdapter<Showcart,ShowCartViewHolder> add;
    private int tot;
    private NavigationView navigationView;
    Cart cartp;
    CardView cartview;
    String post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        proauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ref = database.getReference("carts");
        recyclercart = findViewById(R.id.recylercart);

        layoutManager = new LinearLayoutManager(this);
        recyclercart.setLayoutManager(layoutManager);

        txtprice = findViewById(R.id.total);
        cartview = findViewById(R.id.cartview);
        ccuser = proauth.getCurrentUser();
        /*this.mHandler = new Handler();
        m_Runnable.run();
*/
        // uploadcart();

        //loadproductlist();

        ccuser = proauth.getCurrentUser();

        assert ccuser != null;
        ref.child(ccuser.getUid()).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren())
                        list.add(child.getValue(Order.class));


                    int total = 0;

                    assert list != null;
                    for (Order order : list)
                        total += (Integer.valueOf(order.getPrice())) * (Integer.valueOf(order.getQuantity()));
                    tot = total;
                    Locale locale = new Locale("en", "in");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    txtprice.setText(fmt.format(total));
                }
                else{
                    Toast.makeText(showcart.this,"Your Cart is Empty",Toast.LENGTH_LONG).show();
                    cartview.setVisibility(View.GONE);
                }
            }






            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        loadproduct();

    }

    private void loadproduct() {





        add = new FirebaseRecyclerAdapter<Showcart, ShowCartViewHolder>(Showcart.class,R.layout.cartlayout,ShowCartViewHolder.class,
                ref.child(ccuser.getUid()).child("cart"))
        {
            @Override
            protected void populateViewHolder(ShowCartViewHolder viewHolder, Showcart model, int position) {
                Locale locale = new Locale("en", "in");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);



                viewHolder.txtname.setText(model.getProductname());
                viewHolder.txtprice.setText(fmt.format(Integer.valueOf(model.getPrice())));
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound(""+model.getQuantity(), Color.BLUE);
                viewHolder.imgcount.setImageDrawable(drawable);
                viewHolder.setDeleteitem(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        final DatabaseReference key = getRef(position);
                        post = key.getKey();
                       //Toast.makeText(showcart.this,""+position,Toast.LENGTH_SHORT).show();
                        deletecart(position);
                        /*ref.child(ccuser.getUid()).child("cart").child(post).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                dataSnapshot.getRef().removeValue();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/



                    }
                });

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        add.notifyDataSetChanged();
        recyclercart.setAdapter(add);





    }


   /* private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            //Toast.makeText(showcart.this,"in runnable",Toast.LENGTH_SHORT).show();

            showcart.this.mHandler.postDelayed(m_Runnable,20000);
        }

    };
*/

   private void uploadcart(Order order1) {

        new Database(getBaseContext()).addtocart(order1);
       /* ccuser = proauth.getCurrentUser();
        assert ccuser != null;
        ref.child(ccuser.getUid()).setValue(cart1);
        */

    }

   /*private void loadproductlist() {
    carts = new Database(this).getcarts();
    adapter = new CartAdapter(carts,this);

    adapter.notifyDataSetChanged();

    recyclercart.setAdapter(adapter);


       int total = 0;
    for (Order order:carts)
        total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","in");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtprice.setText(fmt.format(total));
        tot = total;



    }
    */

   /*@Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deletecart(item.getOrder());
        return true;
    }*/

    private void deletecart(int position) {
        carts = new Database(this).getcarts();
       // Toast.makeText(this,""+integer,Toast.LENGTH_SHORT).show();

        carts.remove(position);

        new Database(this).cleancart();

        for (Order order1:carts) {
            new Database(this).addtocart(order1);
            //Toast.makeText(this,""+order1.getProductname(),Toast.LENGTH_SHORT).show();
        }
       Cart cart = new Cart(carts);
        ref.child(proauth.getCurrentUser().getUid()).setValue(cart);


        /*ref.child(proauth.getCurrentUser().getUid()).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                    Pro.add(child.getValue(Order.class));
                new Database(showcart.this).cleancart();
                for (Order order1:Pro) {
                    Toast.makeText(showcart.this,""+order1.getProductname(),Toast.LENGTH_SHORT).show();

                    uploadcart(order1);
                }
                 carts = new Database(getBaseContext()).getcarts();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child(proauth.getCurrentUser().getUid()).child("cart").setValue(carts);
*/


        //add.notifyDataSetChanged();
        //loadproductlist();
        //loadproduct();

    }

    public void placeorder(View view) {


        Intent product = new Intent(showcart.this, Placeorder.class);
        product.putExtra("Total", String.valueOf(tot));
        startActivity(product);

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

        int id = item.getItemId();
        switch (id){
            case R.id.signout:
                new Database(this).cleancart();
                proauth.signOut();

                startActivity(new Intent(showcart.this, LoginActivity.class));
                break;
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
                startActivity(new Intent(showcart.this, Showcategories.class));
                break;
            case R.id.order:
                startActivity(new Intent(showcart.this, Yourorders.class));
                break;
            case R.id.cart:
                finish();
                startActivity(new Intent(showcart.this, showcart.class
                ));
                break;
            case R.id.setting:
                finish();
                startActivity(new Intent(showcart.this, Settings.class));
                break;
            case R.id.about:

                break;

            default:
                startActivity(new Intent(this,Showcategories.class));
                break;
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
