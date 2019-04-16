package com.fekratoday.mashawer.screens.homescreen.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public class TripsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Trip> tripList;

    TripsViewAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.trip_row_layout,viewGroup,false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TextView titleView = viewHolder.itemView.findViewById(R.id.titleView);
        titleView.setText(tripList.get(i).getTitle());
        TextView detailsView =viewHolder.itemView.findViewById(R.id.detailsView);
        detailsView.setText(tripList.get(i).getDetails());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    private class TripViewHolder extends RecyclerView.ViewHolder{
        private View tripView;
        TripViewHolder(@NonNull final View itemView) {
            super(itemView);
            tripView = itemView;
            final ImageButton myBtn =tripView.findViewById(R.id.expandBtn);
            tripView.findViewById(R.id.expandBtn).setOnClickListener(v -> {
                if((itemView.findViewById(R.id.detailsView).getVisibility())== View.GONE){
                    itemView.findViewById(R.id.detailsView).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
                    myBtn.setImageResource(R.drawable.round_keyboard_arrow_up_24px);
                }
                else{
                    itemView.findViewById(R.id.detailsView).setVisibility(View.GONE);
                    itemView.findViewById(R.id.divider).setVisibility(View.GONE);
                    myBtn.setImageResource(R.drawable.round_keyboard_arrow_down_24px);}
            });
        }
    }
}
