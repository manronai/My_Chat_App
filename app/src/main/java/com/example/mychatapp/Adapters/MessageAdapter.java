package com.example.mychatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mychatapp.Model.Chats;
import com.example.mychatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    Context context;
    ArrayList<Chats> chatslist;
    String imageURL;
    public static final int MESSAGE_RIGHT = 0;
    public static final int MESSAGE_LEFT = 1;
    public MessageAdapter(Context context, ArrayList<Chats> chatslist, String imageURL){
        this.chatslist = chatslist;
        this.context = context;
        this.imageURL = imageURL;
    }
    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_RIGHT) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new MyViewHolder(view);


        } else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MyViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        Chats chats  = chatslist.get(position);

        holder.messagetext.setText(chats.getMessage());








    }

    @Override
    public int getItemCount() {
        return chatslist.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView messagetext, seen;
        CircleImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            messagetext = itemView.findViewById(R.id.show_message);
           // imageView = itemView.findViewById(R.id.chat_image);
            seen = itemView.findViewById(R.id.text_Seen);
        }
    }
    @Override
    public int getItemViewType(int position) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (chatslist.get(position).getSender().equals(user.getUid())) {


            return MESSAGE_RIGHT;
        } else {

            return MESSAGE_LEFT;


        }
    }
}


