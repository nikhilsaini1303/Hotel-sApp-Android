package com.example.assignment_1_android;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final ArrayList<HotelData.regionNames> hotelList;
    private final ArrayList<HotelData.hierarchyInfo> hierarchyList;


    public RecyclerAdapter(ArrayList<HotelData.regionNames> hotelList, ArrayList<HotelData.hierarchyInfo> hierarchyList) {
        this.hotelList = hotelList;
        this.hierarchyList = hierarchyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("InflateParams") View inflate = layoutInflater.inflate(R.layout.hotel_lists, null);
        return new ViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HotelData.regionNames hotelData = hotelList.get(position);
        HotelData.hierarchyInfo hierarchyData = hierarchyList.get(position);

        if(Objects.equals(hierarchyData.getCountry().name, null)) {
            holder.countryName.setText("Country : No Name");
        }
        else {
            holder.countryName.setText("Country : " + hierarchyData.getCountry().name);
        }


        holder.fullName.setText("Full Name : " + hotelData.fullName);
        holder.shortName.setText("Short Name : "+ hotelData.shortName);
        holder.displayName.setText("Display Name : "+ hotelData.displayName);
        holder.primaryDisplayName.setText("Primary Name : "+ hotelData.primaryDisplayName);
        holder.secondaryDisplayName.setText("Secondary Name : "+ hotelData.secondaryDisplayName);
        holder.lastSearchName.setText("Last Search Name : "+ hotelData.lastSearchName);

    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{

        TextView fullName, shortName, displayName, primaryDisplayName, secondaryDisplayName, lastSearchName, countryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.country_name);
            fullName = itemView.findViewById(R.id.full_name);
            shortName = itemView.findViewById(R.id.short_name);
            displayName = itemView.findViewById(R.id.display_name);
            primaryDisplayName = itemView.findViewById(R.id.primary_name);
            secondaryDisplayName = itemView.findViewById(R.id.secondary_name);
            lastSearchName = itemView.findViewById(R.id.lastSearch_name);

        }
    }

}
