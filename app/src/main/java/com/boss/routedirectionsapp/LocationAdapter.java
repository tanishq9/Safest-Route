package com.boss.routedirectionsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {
    ArrayList<Loc> arrayList;

    LocationAdapter(ArrayList<Loc> list) {
        this.arrayList = list;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationHolder holder, int position) {
        Loc loc = arrayList.get(position);
        holder.latitude.setText(loc.latitude);
        holder.longitude.setText(loc.longtitude);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        TextView latitude, longitude;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
        }
    }

}
