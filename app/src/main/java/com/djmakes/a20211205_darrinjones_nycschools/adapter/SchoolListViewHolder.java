package com.djmakes.a20211205_darrinjones_nycschools.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.djmakes.a20211205_darrinjones_nycschools.R;
import com.djmakes.a20211205_darrinjones_nycschools.models.School;

public class SchoolListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView schoolName;
    TextView schoolAddress;
    TextView schoolNeighborhood;

   private School school;

    OnSchoolListener onSchoolListener;

    public SchoolListViewHolder(@NonNull View itemView, OnSchoolListener onSchoolListener) {
        super(itemView);
        this.onSchoolListener = onSchoolListener;

        Context context = itemView.getContext();
        schoolName = itemView.findViewById(R.id.txt_school_name);
        schoolAddress = itemView.findViewById(R.id.txt_school_address);
        schoolNeighborhood = itemView.findViewById(R.id.txt_school_neighborhood);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onSchoolListener.onSchoolClick(getAdapterPosition());
    }

}
