package com.example.tileshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();

    EditText userNameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmpasswordET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        int key = bundle.getInt("SECRET_KEY");

        if (key!=99){
            finish();
        }

        preferences = getSharedPreferences(PREF_KEY,MODE_PRIVATE);
        String userName = preferences.getString("userName","");
        String password = preferences.getString("password","");

        userNameET = findViewById(R.id.userNameET);
        emailET = findViewById(R.id.userEmailET);
        passwordET = findViewById(R.id.passwordET);
        confirmpasswordET= findViewById(R.id.passwordConfirmET);

        userNameET.setText(userName);
        passwordET.setText(password);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG,"onCreate");
    }


    public void registerAccount(View view) {
            Log.i(LOG_TAG,"Attempting registration");

            String userName = userNameET.getText().toString();
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            String passwordconfirm = confirmpasswordET.getText().toString();
            //get


            if(!password.equals(passwordconfirm)){
                Log.e(LOG_TAG,"Password-confirmation mismatch");
                return;
            }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG,"User registered with success! " + "Regisztrált: " + userName + " Email: " + email + ", jelszó: " + password);
                    startShopping();
                } else {
                    Log.d(LOG_TAG,"Registration failed");
                }
            }
        });

    }

    private void startShopping(){
        Intent intent = new Intent(this,ShoppingActivity.class);

        startActivity(intent);
    }

    public void cancel(View view) {
        Log.i(LOG_TAG,"Quitting registration process");
        finish();
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(LOG_TAG,"onRestart");
    }


}