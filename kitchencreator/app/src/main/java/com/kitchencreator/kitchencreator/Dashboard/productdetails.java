package com.kitchencreator.kitchencreator.Dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Database.Database;
import com.kitchencreator.kitchencreator.Database.Databasebuy;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.Buynow;
import com.kitchencreator.kitchencreator.model.Cart;
import com.kitchencreator.kitchencreator.model.Order;
import com.kitchencreator.kitchencreator.model.Product;
import com.kitchencreator.kitchencreator.model.Rating;
import com.kitchencreator.kitchencreator.model.Showcart;
import com.kitchencreator.kitchencreator.placeorder.Buynoworder;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class productdetails extends AppCompatActivity implements RatingDialogListener {

    private TextView prodname,prodprice,proddesc;
    private ElegantNumberButton numberButton;
    private ImageView Image;
    private String ProductId="";
    private List<Order> carts = new ArrayList<>();
    private FirebaseDatabase fdb;
    private DatabaseReference proddet,addcart,rate;
    private Product currproduct;
    private Button buynow;
    private FloatingActionButton rating;
    RatingBar ratingBar;

    DatabaseReference requests;
 private CollapsingToolbarLayout collapsingToolbarLayout;
    FirebaseAuth proauth;
    FirebaseUser user;

    String  key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_productdetails);

        fdb = FirebaseDatabase.getInstance();
        proddet = fdb.getReference("product");
        addcart = fdb.getReference("carts");
        requests = fdb.getReference("Requests");
        rate = fdb.getReference("Rating");
        proauth = FirebaseAuth.getInstance();
        user = proauth.getCurrentUser();
        numberButton = findViewById(R.id.quantity);
        buynow = findViewById(R.id.buynow);
        rating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingbar);

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showratingdialog();
            }
        });




        carts = new Database(this).getcarts();
        prodname = findViewById(R.id.Productname);
        prodprice = findViewById(R.id.Productprice);
        proddesc = findViewById(R.id.productdesc);
        Image = findViewById(R.id.productimg);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollaspseAppBar);

        if (getIntent() != null)
        {
            ProductId = getIntent().getStringExtra("productid");
            if (!ProductId.isEmpty() && ProductId != null)
            {
                if(Common.isConnectedtoInternet(getBaseContext()))
                {
                    getproductdetails(ProductId);
                    getRatingfood(ProductId);
            }
            else
                {
                    Toast.makeText(productdetails.this,"Please check your Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    private void getRatingfood(String productId) {

        Query productrate = rate.child(productId).orderByChild("productid").equalTo(productId);

        productrate.addValueEventListener(new ValueEventListener() {
            int count = 0,sum = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                for (DataSnapshot  snapshot : dataSnapshot.getChildren())
                {
                    Rating item = snapshot.getValue(Rating.class);
                    assert item != null;
                   sum+=Integer.parseInt(item.getRatevalue());
                    count++;

                }
                if (count != 0) {
                    float average = sum / count;
                    ratingBar.setRating(average);
                }
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showratingdialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("very Bad","Not Good","Quite Ok","Very Good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this Food")
                .setDescription("Please Some Star and Give Feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please Write Your Comment Here...")
                .setHintTextColor(android.R.color.black)
                .setCommentTextColor(android.R.color.black)
                .setCommentBackgroundColor(R.color.white)
                .setWindowAnimation(R.style.RadioDialogFadeanim)
                .create(productdetails.this)
                .show();
    }

    private void getproductdetails(final String productId) {

        proddet.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currproduct = dataSnapshot.getValue(Product.class);

                assert currproduct != null;
                Picasso.with(getBaseContext()).load(currproduct.getProductimage()).into(Image);

                collapsingToolbarLayout.setTitle(currproduct.getProductname());

                prodname.setText(currproduct.getProductname());
                prodprice.setText(currproduct.getProductprice());
                proddesc.setText(currproduct.getProductdescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   /* public void addcart(View view){
      Order cart = new Order(ProductId,
                currproduct.getProductname(),
                numberButton.getNumber(),
                currproduct.getProductprice(),
                currproduct.getProductdiscount());


        addcart.child(user.getUid()).child("cart").push().setValue(cart);
        Toast.makeText(this,"Added To Cart ",Toast.LENGTH_SHORT).show();

    }*/
   public void addcart(View view) {

        new Database(getBaseContext()).addtocart(new Order(
                ProductId,
                currproduct.getProductname(),
                numberButton.getNumber(),
                currproduct.getProductprice(),
                currproduct.getProductdiscount()
        ));


       uploadcart();
        Toast.makeText(this,"Added To Cart ",Toast.LENGTH_SHORT).show();
    }
    private void uploadcart()
    {
        List<Order> cart = new Database(getBaseContext()).getcarts();
        user = proauth.getCurrentUser();
        assert user != null;
        addcart.child(user.getUid()).child("cart").setValue(cart);
    }


   public void buynow(View view) {

    Bundle extras = new Bundle();
        extras.putString("ProductId",ProductId);
        extras.putString("Productname",currproduct.getProductname());
        extras.putString("Quantity",numberButton.getNumber());
        extras.putString("Productprice",currproduct.getProductprice());
        extras.putString("Productdiscount", currproduct.getProductdiscount());
    Intent intent = new Intent(this,Buynoworder.class);
        intent.putExtras(extras);
    startActivity(intent);
    finish();
}

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        final Rating rating = new Rating(Common.userdetail.phoneno,
                ProductId,
                String.valueOf(i),
                s,
                currproduct.getProductname()
        );
        rate.child(ProductId).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).exists())
                {
                    rate.child(user.getUid()).removeValue();
                   // rate.child(ProductId).child(user.getUid()).setValue(rating);
                }
                else
                {
                    rate.child(ProductId).child(user.getUid()).setValue(rating);
                }
                Toast.makeText(productdetails.this,"Thankyou For Submitting Your Feedback",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }


    /*private void showalertdialog() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Add Detail");
        alertdialog.setMessage("Please Fill Full Information");

        LayoutInflater inflater = this.getLayoutInflater();
        View addlayout = inflater.inflate(R.layout.buynow,null);
        name = addlayout.findViewById(R.id.personname);
        address = addlayout.findViewById(R.id.address);
        pincode = addlayout.findViewById(R.id.Pincode);






        alertdialog.setView(addlayout);
        alertdialog.setIcon(R.drawable.cart);

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Order order = new Order(ProductId,
                        currproduct.getProductname(),
                        numberButton.getNumber(),
                        currproduct.getProductprice(),
                        currproduct.getProductdiscount());
               /* Buynoworder = fdb.getReference("Order");
                Buynoworder.push().setValue(order);
                Buynoworder.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {


                           GenericTypeIndicator<Map<String, Order>> t = new GenericTypeIndicator<Map<String, Order>>() {};
                            Map<String,Order> map = null;
                             map = dataSnapshot.getValue(t);

                                 uploadbuy(map);

                            dataSnapshot.getRef().removeValue();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                assert Common.userdetail.getPhoneno() != null;
*/



           // }
     //   });
      //  alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
       //     @Override
       //     public void onClick(DialogInterface dialog, int which) {
      //          dialog.dismiss();
            }
       // });
      //  alertdialog.show();

   // }


   /* private void uploadbuy(Map<String, Order> map) {

        Buynoworder buynow = new Buynoworder(name.getText().toString(),address.getText().toString(),pincode.getText().toString(), Common.userdetail.getPhoneno(),currproduct.getProductprice()
                ,user.getUid(),Common.userdetail.getCity(), map);
        requests.child(String.valueOf(System.currentTimeMillis())).setValue(buynow);


    }*/



