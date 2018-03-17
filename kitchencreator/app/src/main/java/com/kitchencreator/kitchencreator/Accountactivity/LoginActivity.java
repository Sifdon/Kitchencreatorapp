package com.kitchencreator.kitchencreator.Accountactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kitchencreator.kitchencreator.Common.Common;
import com.kitchencreator.kitchencreator.Dashboard.Popularproducts;
import com.kitchencreator.kitchencreator.Dashboard.Showcategories;
import com.kitchencreator.kitchencreator.Database.Database;
import com.kitchencreator.kitchencreator.R;
import com.kitchencreator.kitchencreator.model.Cart;
import com.kitchencreator.kitchencreator.model.Order;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin,btnregister;
    private EditText input_email,input_password;
    private TextView btnforgetpassword;

FirebaseDatabase mainfdb;
DatabaseReference ref;

    public String email,password;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
try {
    progressDialog = new ProgressDialog(this);
    mainfdb = FirebaseDatabase.getInstance();
    ref = mainfdb.getReference("carts");
    input_email = findViewById(R.id.userEmail);
    input_password = findViewById(R.id.userPassword);
    btnregister = findViewById(R.id.register_button);
    btnforgetpassword = findViewById(R.id.Resetpassword);
    btnLogin = findViewById(R.id.letlogin);
    btnregister.setOnClickListener(this);
    btnforgetpassword.setOnClickListener(this);
    btnLogin.setOnClickListener(this);
    if (Common.isConnectedtoInternet(this)) {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, Showcategories.class));

        }
    } else {
        Toast.makeText(this, "Please check your Intenet Connection", Toast.LENGTH_LONG).show();
    }
}catch (Exception e){
    Log.e("Error",""+e);
}

    }


    @Override
    public void onClick(View view) {
if (Common.isConnectedtoInternet(getBaseContext())){
        if(view == btnforgetpassword)
        {
            startActivity(new Intent(LoginActivity.this,ResetPassword.class));
            finish();
        }
        else if(view == btnregister)
        {
            startActivity(new Intent(LoginActivity.this,registerActivity.class));
            finish();
        }
        else if(view == btnLogin)
        {
            loginUser();

        }
    }
    else {
    Toast.makeText(this,"Please check your Intenet Connection",Toast.LENGTH_LONG).show();
}
    }

    private void loginUser() {
         email = input_email.getText().toString().trim();
         password = input_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_LONG).show();
        }
        else {

            progressDialog.setMessage("Please wait ...");
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (!task.isSuccessful()) {
                                try{

                                        throw task.getException();

                                }
                                catch (FirebaseAuthInvalidCredentialsException e)
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();


                                }
                                catch (Exception e)
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this,"",Toast.LENGTH_LONG).show();

                                }
                            }
                            else {
                                try {


                              /*      FirebaseUser ccuser = auth.getCurrentUser();

                                    assert ccuser != null;

                                    ref.child(ccuser.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild("cart")) {
                                                Common.cart = 22;
                                            }





                                        }



                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });*/

                                    startActivity(new Intent(LoginActivity.this, Showcategories.class));

                                }
                                catch (Exception e)
                                {
                                    Log.e("Error",""+e);
                                }



                            }
                        }
                    });
        }
    }





}
