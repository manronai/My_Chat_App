package com.example.mychatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.example.mychatapp.MessageActivity;
import com.example.mychatapp.Model.Users;
import com.example.mychatapp.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolderr>{

    Context context;
    ArrayList<Users> userlist;
    String friendid;

    public UserAdapter(Context context, ArrayList<Users> usersList, boolean b) {
        this.context = context;
        this.userlist = usersList;

    }


    @NonNull

    @Override
    public MyHolderr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testlayoutofusers, parent, false);
        MyHolderr r=  new MyHolderr(view);
        return  r;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyHolderr holder, int position) {
        Users user = userlist.get(position);




        holder.username.setText(user.getUsername());

        if (user.getImageURL().equals("default")) {



        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Users users = userlist.get(position);
                friendid = users.getId();
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("friendid", friendid);
                Toast.makeText(context, friendid, Toast.LENGTH_LONG).show();
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class MyHolderr extends RecyclerView.ViewHolder {

        TextView username, last_msg;
        CircleImageView imageView, image_on, image_off;
    public  MyHolderr(@NonNull View itemView){
        super(itemView);
        username= itemView.findViewById(R.id.username_userfrag);


    }
    }
}
