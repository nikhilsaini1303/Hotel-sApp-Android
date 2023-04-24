package com.example.assignment_1_android;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password;

    private static final String TAG = "SignUpActivity";

    LoadingAlert loadingAlert = new LoadingAlert(SignUpActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.txt1);
        lastName = findViewById(R.id.txt2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Button signUpButton = findViewById(R.id.btn1);

        signUpButton.setOnClickListener(view -> {

            String f_name = firstName.getText().toString();
            String l_name = lastName.getText().toString();
            String emailV = email.getText().toString();
            String pass = password.getText().toString();

            if(f_name.length() == 0) {
                setToastCustom(false, "Please Enter First Name");
            }
            else if (f_name.charAt(0) == ' ') {
                setToastCustom(false, "Please Enter Valid First Name");
            }
            else if(l_name.length() == 0) {
                setToastCustom(false, "Please Enter Last Name");
            }
            else if (l_name.charAt(0) == ' ') {
                setToastCustom(false, "Please Enter Valid Last Name");
            }
            else if(emailV.length() == 0) {
                setToastCustom(false, "Please Enter Email");
            }
            else if (emailV.charAt(0) == ' ') {
                setToastCustom(false, "Please Enter Valid Email");
            }
            else if(!isValidateEmail(emailV)) {
                setToastCustom(false, "Please include @, ., com");
            }
            else if (pass.length() == 0) {
                setToastCustom(false, "Please Enter Password");
            }
            else if(pass.length() < 8) {
                setToastCustom(false, "Please Enter Password of Length 8");
            }
            else if(!isValidatePassword(pass)) {
                System.out.println("Please Enter Valid Password");
            }
            else {
                loadingAlert.startAlertDialog();
                signUpUser(f_name, l_name, emailV, pass);
            }
        });
    }

    private void signUpUser(String f_Name, String l_Name, String emailUser, String passUser) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(SignUpActivity.this, task -> {
            if(task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();

                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(f_Name).build();
                assert firebaseUser != null;
                firebaseUser.updateProfile(profileChangeRequest);

                ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(l_Name, emailUser);

                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()) {
                        setToastCustom(true, "Successfully SignUp");
                        Intent intent = new Intent(SignUpActivity.this, TableView.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        setToastCustom(false, "User SignUp Failed. Please Try Again.");
                    }
                    loadingAlert.closeAlertDialog();
                });
            }
            else {
                loadingAlert.closeAlertDialog();
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthUserCollisionException e) {
                    setToastCustom(false, "Email is Already Exists. Try Another Email");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    setToastCustom(false, e.getMessage());
                }
            }
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

    public boolean isValidateEmail(String e) {
        String EMAIL_REGEX = "^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher EMAIL_MATCHER = EMAIL_PATTERN.matcher(e);

        return EMAIL_MATCHER.find();
    }

    public boolean isValidatePassword(String pass) {
        int NUM_UPPER_CASE = 1;
        int NUM_LOWER_CASE = 1;
        int NUM_DIGIT = 1;
        int NUM_SPECIAL_CHARACTER = 1;

        int lowerCount = 0;
        int upperCount = 0;
        int digitCount = 0;
        int specialCount = 0;

        for(int i = 0; i < pass.length(); i++) {
            char ch = pass.charAt(i);

            if(upperCount >= NUM_UPPER_CASE && lowerCount >= NUM_LOWER_CASE && digitCount >= NUM_DIGIT && specialCount >= NUM_SPECIAL_CHARACTER) {
                return true;
            }

            else if(isLetter(ch)) {
                if(isUpperCase(ch)) {
                    upperCount++;
                }
                if(isLowerCase(ch)) {
                    lowerCount++;
                }
            }
            else if(isDigit(ch)) {
                digitCount++;
            }

            else {
                specialCount++;
            }



        }

        if(upperCount == 0) {
            setToastCustom(false, "Enter At-least One Upper Case Letter Like A, B, C,...");
        }
        else if (lowerCount == 0) {
            setToastCustom(false, "Enter At-least One Lower Case Letter Like a, b, c,...");
        }
        else if (digitCount == 0) {
            setToastCustom(false, "Enter At-least One Number Like 1, 2, 3,...");
        }
        else if (specialCount == 0) {
            setToastCustom(false, "Enter At-least One Special Letter Like @, #, $, !,...");
        }

        return  false;

    }

}