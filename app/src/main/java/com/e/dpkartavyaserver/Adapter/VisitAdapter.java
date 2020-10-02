package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Model.VerifySnr;
import com.e.dpkartavyaserver.R;
import com.e.dpkartavyaserver.Model.Visit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Visit> orders;
    public VisitAdapter(Context context, ArrayList<Visit> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_visit,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.mob.setText(orders.get(position).getMob());
        holder.time.setText(orders.get(position).getTime());
        holder.date.setText(orders.get(position).getDate());
        holder.notes.setText(orders.get(position).getNotes());
        try {
            Picasso.get()
                    .load(orders.get(position).getPhoto())
                    .into(holder.img);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView name,mob,addr,time,date,notes;
        ImageView img;
        CardView cardView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.visitName);
            mob = itemView.findViewById(R.id.visitMob);
            img = itemView.findViewById(R.id.visitImg);
            date = itemView.findViewById(R.id.visitDate);
            time = itemView.findViewById(R.id.visitTime);
            notes = itemView.findViewById(R.id.visitNotes);
            cardView = itemView.findViewById(R.id.visitGeoLocation);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Visit snr = orders.get(getAdapterPosition());
                    String ss ="geo:0,0?q="+snr.getLoc().getLatitude()+","+snr.getLoc().getLongitude()+",(Location)";
                    // String u = "geo:"+snr.getMoreDetails().getLoc().getLatitude()+","+snr.getMoreDetails().getLoc().getLongitude()+"?q=";
                    Uri gmmIntentUri = Uri.parse(ss);
// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                    mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to star an activity that can handle the Intent
                    context.startActivity(mapIntent);
                }
            });
        }
    }
}
