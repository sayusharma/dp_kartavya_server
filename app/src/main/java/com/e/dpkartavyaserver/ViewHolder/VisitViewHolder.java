package com.e.dpkartavyaserver.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.R;


public class VisitViewHolder extends RecyclerView.ViewHolder{
   public TextView name,mob,time,date,notes,complaint;
   public ImageView img;
   public CardView cardView;
    public VisitViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.visitName);
        mob = itemView.findViewById(R.id.visitMob);
        cardView = itemView.findViewById(R.id.visitGeoLocation);
        img = itemView.findViewById(R.id.visitImg);
        date = itemView.findViewById(R.id.visitDate);
        time = itemView.findViewById(R.id.visitTime);
        notes = itemView.findViewById(R.id.visitNotes);
        complaint = itemView.findViewById(R.id.visitComplaint);
    }

}
