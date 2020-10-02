package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Model.Children;
import com.e.dpkartavyaserver.R;

import java.util.ArrayList;

public class PreviewChildrenAdapter extends RecyclerView.Adapter<PreviewChildrenAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Children> orders;
    public PreviewChildrenAdapter(Context context, ArrayList<Children> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.preview_children,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.mob.setText(orders.get(position).getMob());
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
        TextView name,mob,pos,addr;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pchildName);
            mob = itemView.findViewById(R.id.pchildMob);
            pos = itemView.findViewById(R.id.pchildPos);
            addr = itemView.findViewById(R.id.pchildAddr);
        }
    }

}
