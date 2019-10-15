package com.tetra.mapdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;
    private EditText userName;
    private EditText userPassword;
    private ProgressDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.password);
        Button userLogin = findViewById(R.id.loginUser);
        TextView singUpUser = findViewById(R.id.signUpUser);
        //Intent intent = new Intent(MainActivity.this, MapsActivity.class);
       // startActivity(intent);

        userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        loadingDialog = new ProgressDialog(this);


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userNameString = userName.getText().toString();
                String password = userPassword.getText().toString();
                if (TextUtils.isEmpty(userNameString) || !android.util.Patterns.EMAIL_ADDRESS.matcher(userNameString).matches()) {
                    userName.setError("Please enter a valid email id");
                    return;
                }

                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    userPassword.setError("Password min 6 characters");
                    return;
                }

                userName.setError(null);
                userPassword.setError(null);

                loginUser(userNameString, password);


            }
        });

        singUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameString = userName.getText().toString();
                String password = userPassword.getText().toString();
                if (TextUtils.isEmpty(userNameString) || !android.util.Patterns.EMAIL_ADDRESS.matcher(userNameString).matches()) {
                    userName.setError("Please enter a valid email id");
                    return;
                }

                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    userPassword.setError("Password min 6 characters");
                    return;
                }

                userName.setError(null);
                userPassword.setError(null);

                signUpUser(userNameString, password);
            }
        });


    }

    public void loginUser(String email, String password) {


        loadingDialog.setTitle("Logging In");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                loadingDialog.dismiss();

                                if (task.isSuccessful()) {


                                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("Login Failed")
                                            .setMessage(task.getException().getMessage())
                                            .setPositiveButton("DISMISS", null)
                                            .show();
                                }
                            }
                        });


    }

    public void signUpUser(String email, String password) {

        loadingDialog.setTitle("Signing Up");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        Log.d("Hey","Heloooo");

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        loadingDialog.dismiss();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Sign Up Failed")
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton("DISMISS", null)
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

