package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.R;
import com.e.dpkartavyaserver.Model.Visit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Visit> orders;
    OnItemClickListener onItemClickListener;

    public VisitAdapter(Context context, ArrayList<Visit> orders, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.orders = orders;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_visit,parent,false),onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.mob.setText(orders.get(position).getMob());
        holder.addr.setText(orders.get(position).getAddr());
        holder.time.setText(orders.get(position).getTime());
        holder.date.setText(orders.get(position).getDate());
        holder.notes.setText(orders.get(position).getNotes());
        Picasso.get()
                .load(orders.get(position).getPhoto())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,mob,addr,time,date,notes;
        OnItemClickListener onItemClickListener;
        ImageView img;
        public OrderViewHolder(@NonNull View itemView,OnItemClickListener itemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.visitName);
            mob = itemView.findViewById(R.id.visitMob);
            img = itemView.findViewById(R.id.visitImg);
            date = itemView.findViewById(R.id.visitDate);
            time = itemView.findViewById(R.id.visitTime);
            addr = itemView.findViewById(R.id.visitAddress);
            notes = itemView.findViewById(R.id.visitNotes);
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
