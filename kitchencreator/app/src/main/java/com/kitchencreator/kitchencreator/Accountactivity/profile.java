package com.kitchencreator.kitchencreator.Accountactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kitchencreator.kitchencreator.Dashboard.Popularproducts;
import com.kitchencreator.kitchencreator.Dashboard.Showcategories;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.UserInformation;

public class profile extends AppCompatActivity {

private EditText fullname,phoneno,address,city,pincode,state,country;

private Button save;
private DatabaseReference ref;
private FirebaseAuth proauth;
private ProgressDialog progressDialog;
private FirebaseDatabase firedb;
public  String uname,phone,uaddress,ucity,ustate,Country,upin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        proauth = FirebaseAuth.getInstance();
        firedb = FirebaseDatabase.getInstance();
        fullname = findViewById(R.id.userfullname);
        phoneno = findViewById(R.id.userphoneno);
        address = findViewById(R.id.useraddress);
        pincode = findViewById(R.id.userpincode);
        city = findViewById(R.id.userCity);

        state = findViewById(R.id.userstate);
        country = findViewById(R.id.userCountry);
        save = findViewById(R.id.datasave);
        ref = FirebaseDatabase.getInstance().getReference();


        progressDialog = new ProgressDialog(this);

    }

    private void savedatainformation(){

         uname=fullname.getText().toString().trim();
         phone=phoneno.getText().toString().trim();
         uaddress=address.getText().toString().trim();
         ucity=city.getText().toString().trim();
         ustate=state.getText().toString().trim();
         upin = pincode.getText().toString().trim();
         Country=country.getText().toString().trim();


         if (uname.isEmpty() ||phone.isEmpty() ||uaddress.isEmpty() ||ucity.isEmpty() ||ustate.isEmpty() ||upin.isEmpty() ||Country.isEmpty() ) {
             Toast.makeText(this, "Please Enter Complete details", Toast.LENGTH_SHORT).show();
         }else{
             UserInformation userInformation = new UserInformation(uname, phone, uaddress, ucity, upin, ustate, Country);

             FirebaseUser cuuser = proauth.getCurrentUser();
             ref.child("users").child(cuuser.getUid()).setValue(userInformation);
             finish();
             startActivity(new Intent(profile.this, Showcategories.class));
         }




    }

    public void detailsave(View view) {

        savedatainformation();


    }


}
