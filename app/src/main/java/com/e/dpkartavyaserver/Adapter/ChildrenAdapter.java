package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Model.Children;
import com.e.dpkartavyaserver.R;

import java.util.ArrayList;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Children> orders;
    public ChildrenAdapter(Context context, ArrayList<Children> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_children,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.mob.setText(orders.get(position).getMob());
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
        TextView name,mob,pos;
        ImageView imageView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.childName);
            mob = itemView.findViewById(R.id.childMob);
            pos = itemView.findViewById(R.id.childPos);
            imageView = itemView.findViewById(R.id.cross);
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
