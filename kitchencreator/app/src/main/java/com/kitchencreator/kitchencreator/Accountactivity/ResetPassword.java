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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kitchencreator.kitchencreator.R;

public class ResetPassword extends AppCompatActivity {
    private Button forpass;         //forpass for forget password
    private EditText foremail;
    private ProgressDialog progressDialog;
    private FirebaseAuth resauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        progressDialog = new ProgressDialog(this);
        resauth=FirebaseAuth.getInstance();
        foremail = findViewById(R.id.resetEmail);
        forpass = findViewById(R.id.Resetpass);


    }

    public void letsSave(View view) {

        String email = foremail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        resauth.sendPasswordResetEmail(email)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPassword.this,LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }


                    }

                });
}
}
