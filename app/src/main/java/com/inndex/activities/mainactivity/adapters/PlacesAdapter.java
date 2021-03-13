package com.inndex.activities.mainactivity.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.R;
import com.inndex.retrofit.responseapifoursquare.LocationResposePlaceFourSquare;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewholder> {

    ArrayList<LocationResposePlaceFourSquare> places;
    Activity activity;
    SearchView searchView;
    RecyclerView recyclerView;

    public PlacesAdapter(ArrayList<LocationResposePlaceFourSquare> places, Activity activity, SearchView searchView, RecyclerView recyclerView) {
        this.places = places;
        this.activity = activity;
        this.searchView = searchView;
        this.recyclerView = recyclerView;
    }

    public PlacesAdapter() {
    }

    @NonNull
    @Override
    public PlacesAdapter.PlacesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemplaces, parent, false);
        return new PlacesViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.PlacesViewholder holder, int position) {

        final LocationResposePlaceFourSquare placeFourSquare = places.get(position);

        holder.nombre.setText(placeFourSquare.getName());

        holder.direccion.setText(placeFourSquare.getLocation().getAdrres());



        //todo
        holder.itemView.setOnClickListener(v ->{
            searchView.setQuery(placeFourSquare.getName(), false);
            recyclerView.setVisibility(View.GONE);

        });

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class PlacesViewholder extends  RecyclerView.ViewHolder{


        TextView nombre;
        TextView direccion;

        public PlacesViewholder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_place);
            direccion = itemView.findViewById(R.id.direccion_place);

        }
    }
}
