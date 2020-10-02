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

import com.e.dpkartavyaserver.Model.Visit;
import com.e.dpkartavyaserver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VisitInsightsAdapter extends RecyclerView.Adapter<VisitInsightsAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Visit> orders;
    public VisitInsightsAdapter(Context context, ArrayList<Visit> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_visit_insights,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.off_name.setText(orders.get(position).getOff_name());
        holder.off_mob.setText(orders.get(position).getOff_mob());
        holder.mob.setText(orders.get(position).getMob());
        holder.comp.setText(orders.get(position).getComplaint());
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
        TextView name,mob,off_name,off_mob,time,date,notes,comp;
        ImageView img;
        CardView cardView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            comp = itemView.findViewById(R.id.visitIComplaint);
            name = itemView.findViewById(R.id.visitIName);
            mob = itemView.findViewById(R.id.visitIMob);
            img = itemView.findViewById(R.id.visitIImg);
            date = itemView.findViewById(R.id.visitIDate);
            time = itemView.findViewById(R.id.visitITime);
            off_mob = itemView.findViewById(R.id.visitOffMob);
            off_name = itemView.findViewById(R.id.visitOffName);
            notes = itemView.findViewById(R.id.visitINotes);
            cardView = itemView.findViewById(R.id.visitIGeoLocation);
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
