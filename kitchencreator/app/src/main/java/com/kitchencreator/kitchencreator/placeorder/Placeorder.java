package com.kitchencreator.kitchencreator.placeorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Common.Config;
import com.kitchencreator.kitchencreator.Database.Database;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.Cart;
import com.kitchencreator.kitchencreator.model.Order;
import com.kitchencreator.kitchencreator.model.Requests;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Placeorder extends AppCompatActivity {

    private EditText name,address,pincode,city,phoneno;
    FirebaseDatabase fdb;
    DatabaseReference requests,addcart;
    FirebaseAuth proauth;
    private List<Order> products = new ArrayList<>();
    FirebaseUser user;
    String  total;
    String paymentmode;
    RadioButton rdicod,rdipay;;
    CheckBox userdetails;

    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    private static final int PAYPAL_REQUEST_CODE = 9999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);
        fdb = FirebaseDatabase.getInstance();
        proauth = FirebaseAuth.getInstance();
        user  = proauth.getCurrentUser();
        requests = fdb.getReference("Requests");
        addcart = fdb.getReference("carts");
        rdicod = findViewById(R.id.cashondelivery);
        rdipay = findViewById(R.id.paypal);


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        addcart.child(user.getUid()).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                    products.add(child.getValue(Order.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(getIntent() != null){
            total = getIntent().getStringExtra("Total");

        }
        name = findViewById(R.id.personname);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.Pincode);
        city = findViewById(R.id.City);
       phoneno = findViewById(R.id.phoneno);
         userdetails = findViewById(R.id.usercheck);


         userdetails.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (userdetails.isChecked())
                     onCheckboxClicked();
                 else
                     onunCheckboxClicked();
             }
         });

       rdicod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked)
               paymentmode = "Cash on delivery";
           }
       });
       rdipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked)
                  paymentmode = "paypal";
           }
       });

    }

    private void onunCheckboxClicked() {

        name.setText("");
        address.setText("");
        pincode.setText("");
        city.setText("");
        phoneno.setText("");
    }

    public void placeorder(View view) {
        if ( !name.getText().toString().trim().isEmpty() && !address.getText().toString().trim().isEmpty() &&
                !pincode.getText().toString().trim().isEmpty() &&  !city.getText().toString().isEmpty() &&
                !phoneno.getText().toString().isEmpty()){
        if (!rdicod.isChecked() && !rdipay.isChecked()) {


            Toast.makeText(this,"Please Payment Mode",Toast.LENGTH_SHORT).show();



        }else
            if (rdipay.isChecked()){
                paypal();
                }
                else if (rdicod.isChecked()){
                String paymentstate =  "not approved";
            Requests request = new Requests(
                    name.getText().toString().trim(), address.getText().toString().trim(), pincode.getText().toString().trim(),
                    city.getText().toString(),
                    phoneno.getText().toString(), total, user.getUid(),
                    "Cash on delivery",
                    "unpaid"
                    , products);
            requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
            cleancart();
            }
        }


    else{
            Toast.makeText(this,"Enter the complete details",Toast.LENGTH_SHORT).show();
        }
    }

    private void paypal() {
            float condoll = (float) Math.ceil(Integer.parseInt(total)/Common.dollar);
        String formatamount = String.valueOf(condoll).replace(",","").replace("₹","");

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatamount),"USD","Kitchencreator order app",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null)
                {
                    try {
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        JSONObject jsonObject = new JSONObject(paymentDetail);
                        Requests request = new Requests(
                                name.getText().toString().trim(), address.getText().toString().trim(), pincode.getText().toString().trim(), city.getText().toString(),
                                phoneno.getText().toString(), total,  "paypal",user.getUid(), jsonObject.getJSONObject("response").getString("state"), products
                        );
                        requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                        cleancart();
                        Toast.makeText(this,"Thanks,Order is placed",Toast.LENGTH_SHORT).show();
                        finish();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            }else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Payment cancel",Toast.LENGTH_SHORT).show();

            }
            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            {
                Toast.makeText(this,"Invalid Payment",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cleancart() {

        addcart.child(user.getUid()).removeValue();
        new Database(getBaseContext()).cleancart();

    }

    public void onCheckboxClicked() {

        name.setText(Common.userdetail.fullname);
        address.setText(Common.userdetail.address);
        pincode.setText(Common.userdetail.pincode);
        city.setText(Common.userdetail.city);
        phoneno.setText(Common.userdetail.phoneno);
    }
}
