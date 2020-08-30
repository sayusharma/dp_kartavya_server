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
import com.e.dpkartavyaserver.Model.Visit;
import com.e.dpkartavyaserver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TodayVisitAdapter extends RecyclerView.Adapter<TodayVisitAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Visit> orders;
    OnItemClickListener onItemClickListener;

    public TodayVisitAdapter(Context context, ArrayList<Visit> orders, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.orders = orders;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.today_visit_item,parent,false),onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.name.setText(orders.get(position).getName());
        holder.offMob.setText(orders.get(position).getOff_mob());
        holder.offName.setText(orders.get(position).getOff_name());
        holder.addr.setText(orders.get(position).getAddr());
        holder.time.setText(orders.get(position).getTime());
        holder.mob.setText(orders.get(position).getMob());
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
        TextView name,mob,addr,time,offMob,offName,notes;
        OnItemClickListener onItemClickListener;
        ImageView img;
        public OrderViewHolder(@NonNull View itemView,OnItemClickListener itemClickListener) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.todayVisitName);
            mob =  (TextView) itemView.findViewById(R.id.todayVisitMob);
            img =  (ImageView) itemView.findViewById(R.id.todayVisitPhoto);
            offName = itemView.findViewById(R.id.todayVisitOffName);
            notes = itemView.findViewById(R.id.todayVisitNotes);
            time = itemView.findViewById(R.id.todayVisitTime);
            addr = itemView.findViewById(R.id.todayVisitAddr);
            offMob = itemView.findViewById(R.id.todayVisitOffMob);
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
