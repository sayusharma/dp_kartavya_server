package com.e.dpkartavyaserver.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Interface.ItemClickListener;
import com.e.dpkartavyaserver.R;


public class SignUpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name,rank,mob;
    public ImageView img;
    private ItemClickListener itemClickListener;
    public SignUpViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.signUpName);
        mob =  (TextView) itemView.findViewById(R.id.signUpMob);
        img =  (ImageView) itemView.findViewById(R.id.signUpPhoto);
        rank =  (TextView) itemView.findViewById(R.id.signUpRank);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
