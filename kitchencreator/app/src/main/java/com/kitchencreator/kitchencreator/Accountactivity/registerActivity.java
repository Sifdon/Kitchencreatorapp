package com.kitchencreator.kitchencreator.Accountactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.kitchencreator.kitchencreator.R;

import java.util.Objects;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {

private Button register;
private EditText regemail;
private EditText regpassword;
private EditText conpassword;
private TextView alreadyreg;    //for those who had already registered
    private FirebaseAuth mauth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mauth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        regemail = findViewById(R.id.userregEmail);
        regpassword = findViewById(R.id.userregPassword);
        conpassword = findViewById(R.id.userregConfirm);
        register = findViewById(R.id.letsregister);
        alreadyreg = findViewById(R.id.alreadyregister);

        register.setOnClickListener(this);
        alreadyreg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if(view == register)
        {
            registeruser();
        }
        if(view == alreadyreg)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

    }

    private void registeruser() {
        String email = regemail.getText().toString().trim();
        String password = regpassword.getText().toString();
        String confirmpassword = conpassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_LONG).show();
            return;
        }
                else if(!confirmpassword.equals(password))
                {
            Toast.makeText(this,"Password does not matched",Toast.LENGTH_LONG).show();
            return;
                 } else {
        progressDialog.setMessage("Registering user ...");
        progressDialog.show();

            mauth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (!task.isSuccessful())
                                    {
                                        try
                                        {
                                            throw task.getException();
                                        }
                                        // if user enters wrong email.
                                        catch (FirebaseAuthWeakPasswordException weakPassword)
                                        {   progressDialog.dismiss();
                                            Toast.makeText(registerActivity.this,"Weak Password",Toast.LENGTH_LONG).show();

                                            // TODO: take your actions!
                                        }
                                        // if user enters wrong password.
                                        catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                        {   progressDialog.dismiss();
                                            Toast.makeText(registerActivity.this,"Malformed Email",Toast.LENGTH_LONG).show();

                                            // TODO: Take your action
                                        }
                                        catch (FirebaseAuthUserCollisionException existEmail)
                                        {   progressDialog.dismiss();
                                            Toast.makeText(registerActivity.this,"Email already exists",Toast.LENGTH_LONG).show();

                                            // TODO: Take your action
                                        }
                                        catch (Exception e)
                                        {   progressDialog.dismiss();
                                            Toast.makeText(registerActivity.this,"registeration failed",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        finish();
                                        startActivity(new Intent(registerActivity.this,profile.class));
                                    }
                                }
                            }
                    );

    }
    }
}
