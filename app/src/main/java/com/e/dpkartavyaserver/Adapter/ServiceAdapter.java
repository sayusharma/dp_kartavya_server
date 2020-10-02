package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.dpkartavyaserver.Model.ServiceProvider;
import com.e.dpkartavyaserver.R;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.OrderViewHolder>{
    Context context;
    ArrayList<ServiceProvider> orders;
    public ServiceAdapter(Context context, ArrayList<ServiceProvider> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_service_provider,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.ser.setText(orders.get(position).getSerType());
        holder.pos.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        try {
            return orders.size();
        }catch (Exception e){
            return 0;
        }
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,ser,pos;
        ImageView imageView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.serName);
            ser = itemView.findViewById(R.id.serType);
            pos = itemView.findViewById(R.id.serPos);
            imageView = itemView.findViewById(R.id.sercross);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            orders.remove(getPosition());
            notifyItemRemoved(getPosition());
            notifyItemRangeChanged(getPosition(),orders.size());
        }
    }

}
