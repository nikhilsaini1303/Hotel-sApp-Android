package com.example.assignment_1_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableView extends AppCompatActivity {

    private FirebaseAuth authProfile;

    private RecyclerView recyclerView;

    private ArrayList<HotelData.regionNames> hotelData;

    private ArrayList<HotelData.hierarchyInfo> hierarchyData;

    private EditText location;

    LoadingAlert loadingAlert = new LoadingAlert(TableView.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);

        Button logout_Button = findViewById(R.id.logout_B);

        recyclerView = findViewById(R.id.recyclerView);



        location = findViewById(R.id.edit_text);

        authProfile = FirebaseAuth.getInstance();

        Button search_Button = findViewById(R.id.search_Button);

        search_Button.setOnClickListener(view -> {

            loadingAlert.startAlertDialog();
            hotelData = new ArrayList<>();
            hierarchyData = new ArrayList<>();

            String loc = location.getText().toString();

            if(loc.length() == 0) {
                setToastCustom(false, "Please Enter Location");
                loadingAlert.closeAlertDialog();
                return;
            }

            myAPI API = RetrofitClient.getRetrofitInstance().create(myAPI.class);



            Call<HotelData> call = API.getAllData(loc,
                    "en_US",
                    1033,
                    "300000001",
                    "hotels4.p.rapidapi.com",
                    "b7f0c098d1msh8000a209160b699p160cb6jsnbff91d2e5955");

            call.enqueue(new Callback<HotelData>() {
                @Override
                public void onResponse(@NonNull Call<HotelData> call, @NonNull Response<HotelData> response) {

                    if(response.isSuccessful()) {
                        assert response.body() != null;
                        ArrayList<HotelData.sr> data = response.body().getSr();

                        if(data.size() == 0) {
                            setToastCustom(false, "Location doesn't Exists. Enter Correct Location");
                            loadingAlert.closeAlertDialog();
                            return;
                        }

                        for (HotelData.sr data1 : data) {
                            hotelData.add(data1.regionNames);
                            hierarchyData.add(data1.hierarchyInfo);
                        }

                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(hotelData, hierarchyData);

                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(TableView.this));
                    }
                    else {
                        setToastCustom(false, "Location doesn't Exists");
                    }

                    loadingAlert.closeAlertDialog();
                    location.setText("");

                }

                @Override
                public void onFailure(@NonNull Call<HotelData> call, @NonNull Throwable t) {
                    loadingAlert.closeAlertDialog();
                    setToastCustom(false, t.getMessage());
                }
            });
        });





        logout_Button.setOnClickListener(view -> {
            authProfile.signOut();
            setToastCustom(true, "Successfully Logged Out");
            finish();
            startActivity(new Intent(TableView.this, MainActivity.class));
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