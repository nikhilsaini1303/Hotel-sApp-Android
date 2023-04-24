package com.example.assignment_1_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth authProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authProfile = FirebaseAuth.getInstance();

        Button signupButton = findViewById(R.id.btn1);

        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });


        Button loginButton = findViewById(R.id.btn2);

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authProfile.getCurrentUser() != null) {
            setToastCustom(true, "You are Already Logged In");
            Intent intent = new Intent(MainActivity.this, TableView.class);
            startActivity(intent);
            finish();
        }
    }

    public void setToastCustom(boolean flag, String toast_Text) {
        LayoutInflater inflater = getLayoutInflater();
        Toast toast = new Toast(getApplicationContext());
        View layout;
        if(!flag) {
            layout = inflater.inflate(R.layout.toast_layout_cross, findViewById(R.id.toast_C));
            TextView tv  = layout.findViewById(R.id.tv_toastC);
            tv.setText(toast_Text);

        }
        else {
            layout = inflater.inflate(R.layout.toast_layout_check, findViewById(R.id.toast_R));
            TextView tv  = layout.findViewById(R.id.tv_toastR);
            tv.setText(toast_Text);

        }
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 700);
        toast.show();
    }
}