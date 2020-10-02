package com.e.dpkartavyaserver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Model.VerifySnr;
import com.e.dpkartavyaserver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeniorAdapter extends RecyclerView.Adapter<SeniorAdapter.OrderViewHolder>{
    Context context;
    ArrayList<VerifySnr> orders;
    OnItemClickListener onItemClickListener;

    public SeniorAdapter(Context context, ArrayList<VerifySnr> orders, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.orders = orders;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.snr_czn_item,parent,false),onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getBasicDetails().getPersonalDetails().getName());
        holder.mob.setText(orders.get(position).getBasicDetails().getPersonalDetails().getMob());
        try {
            Picasso.get()
                    .load(orders.get(position).getBasicDetails().getPersonalDetails().getPhoto())
                    .into(holder.img);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,mob;
        OnItemClickListener onItemClickListener;
        ImageView img;
        public OrderViewHolder(@NonNull View itemView,OnItemClickListener itemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.snrCznName);
            mob = itemView.findViewById(R.id.snrCznMob);
            img = itemView.findViewById(R.id.snrCznImg);
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
