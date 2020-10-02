package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Model.ServiceProvider;
import com.e.dpkartavyaserver.R;

import java.util.ArrayList;

public class PreviewServiceAdapter extends RecyclerView.Adapter<PreviewServiceAdapter.OrderViewHolder>{
    Context context;
    ArrayList<ServiceProvider> orders;
    public PreviewServiceAdapter(Context context, ArrayList<ServiceProvider> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.preview_service_provider,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.ser.setText(orders.get(position).getSerType());
        holder.status.setText(orders.get(position).getVerStatus());
        holder.notes.setText(orders.get(position).getVerNotes());
        holder.pos.setText(String.valueOf(position+1));
        holder.addr.setText(orders.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        if(orders.size()!=0) {
            return orders.size();
        }
        else return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView name,ser,pos,status,notes,addr;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pserName);
            ser = itemView.findViewById(R.id.pserType);
            pos = itemView.findViewById(R.id.pserPos);
            status = itemView.findViewById(R.id.pserVerStatus);
            notes = itemView.findViewById(R.id.pserVerNotes);
            addr = itemView.findViewById(R.id.pserAddr);
        }
    }

}
