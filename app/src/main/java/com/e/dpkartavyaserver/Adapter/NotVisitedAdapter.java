package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Model.LastVisit;
import com.e.dpkartavyaserver.Model.User;
import com.e.dpkartavyaserver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotVisitedAdapter extends RecyclerView.Adapter<NotVisitedAdapter.OrderViewHolder>{
    Context context;
    ArrayList<LastVisit> orders;
    OnItemClickListener onItemClickListener;

    public NotVisitedAdapter(Context context, ArrayList<LastVisit> orders, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.orders = orders;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_not_visit,parent,false),onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.mob.setText(orders.get(position).getMob());
        holder.police.setText(orders.get(position).getPolice());
        holder.date.setText(orders.get(position).getDate());
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

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,mob,date,police;
        OnItemClickListener onItemClickListener;
        ImageView img;
        public OrderViewHolder(@NonNull View itemView,OnItemClickListener itemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.nVName);
            mob = itemView.findViewById(R.id.nVMob);
            img = itemView.findViewById(R.id.nVImg);
            date = itemView.findViewById(R.id.nVDate);
            police = itemView.findViewById(R.id.nVPolice);
            this.onItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(getAdapterPosition());

        }
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
}
