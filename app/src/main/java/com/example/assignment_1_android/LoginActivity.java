package com.example.assignment_1_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth authProfile;

    private static final String TAG = "LoginActivity";

    LoadingAlert loadingAlert = new LoadingAlert(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        authProfile = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.btn1);


        loginButton.setOnClickListener(view -> {

            String emailV = email.getText().toString();
            String pass = password.getText().toString();
            if(emailV.length() == 0) {
                setToastCustom(false, "Please Enter Email");
            }
            else if (emailV.charAt(0) == ' ') {
                setToastCustom(false, "Please Enter Valid Email");
            }
            else if (pass.length() == 0) {
                setToastCustom(false, "Please Enter Password");
            }
            else {

                loadingAlert.startAlertDialog();
                loginUser(emailV, pass);
            }
        });

    }

    private void loginUser(String emailUser, String passUser) {

        authProfile.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(LoginActivity.this, task -> {
            if(task.isSuccessful()) {
                setToastCustom(true, "You Are Logged in Now");
                Intent intent = new Intent(LoginActivity.this, TableView.class);
                startActivity(intent);
                finish();
            }
            else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException e) {
                    setToastCustom(false, "User does not exists. Please SignUp");
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    setToastCustom(false, "Incorrect Password");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    setToastCustom(false, e.getMessage());
                }
            }
            loadingAlert.closeAlertDialog();
        });


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