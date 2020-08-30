package com.e.dpkartavyaserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.e.dpkartavyaserver.Interface.ItemClickListener;
import com.e.dpkartavyaserver.Model.User;
import com.e.dpkartavyaserver.ViewHolder.SignUpViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SignUpRequestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private Context context;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_requests);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("requests");
        recyclerView = findViewById(R.id.signUpRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        context = this;
        FirebaseRecyclerAdapter<User, SignUpViewHolder> adapter = new FirebaseRecyclerAdapter<User, SignUpViewHolder>(User.class,
                R.layout.sign_up_item,SignUpViewHolder.class,databaseReference.orderByKey()) {
            @Override
            protected void populateViewHolder(SignUpViewHolder signUpViewHolder, User user, int i) {
                    signUpViewHolder.name.setText(user.getName());
                    signUpViewHolder.mob.setText(user.getMob());
                    signUpViewHolder.rank.setText(user.getRank());
                Picasso.get()
                        .load(user.getPhoto())
                        .into(signUpViewHolder.img);
                final User clickItem = user;
                signUpViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                            SignUpDialog signUpDialog = new SignUpDialog(clickItem);
                            signUpDialog.showDialog((Activity) context);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public class SignUpDialog extends AppCompatActivity {
        public RelativeLayout back,accept,reject;
        public User clickItem;
        public SignUpDialog(User clickItem){
            this.clickItem=clickItem;
        }
        public void showDialog(final Activity activity){
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert_request);
                back = dialog.findViewById(R.id.back);
                accept = dialog.findViewById(R.id.accept);
                reject = dialog.findViewById(R.id.reject);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItem(clickItem);
                        addToUsers(clickItem);
                        dialog.dismiss();
                    }
                });
                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItem(clickItem);
                        dialog.dismiss();
                    }
                });
                dialog.show();
        }
    }
    public void removeItem(User item){
        databaseReference.child(item.getMob()).removeValue();
        DatabaseReference databaseReference1 = firebaseDatabase.getReference("rejected");
        databaseReference1.child(item.getMob()).setValue(item);
    }
    public void addToUsers(User item){
        DatabaseReference databaseReference1 = firebaseDatabase.getReference("users");
        databaseReference1.child(item.getMob()).setValue(item);
    }
    public void onClickBackSignUp(View view){
        Intent intent = new Intent(SignUpRequestActivity.this,DashActivity.class);
        startActivity(intent);
        finish();
    }
}