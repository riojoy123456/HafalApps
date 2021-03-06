package com.example.hafalapps.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafalapps.R;
import com.example.hafalapps.PenilaianActivity;
import com.example.hafalapps.models.ModelSetoran;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class AdapterSetoranlist extends RecyclerView.Adapter<AdapterSetoranlist.MyHolder> {

    Context context;
    List<ModelSetoran> userList; //get user info
    private HashMap<String, String> lastMessageMap;

    //contructor
    public AdapterSetoranlist(Context context, List<ModelSetoran> userList) {
        this.context = context;
        this.userList = userList;
        lastMessageMap = new HashMap<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout row setoranlist
        View view = LayoutInflater.from(context).inflate(R.layout.row_setoranlist, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        //get data
        final String hisUid = userList.get(i).getUid();
        String userImage = userList.get(i).getImage();
        String userName = userList.get(i).getName();
        String lastMessage = lastMessageMap.get(hisUid);

        //set data
        myHolder.nameTv.setText(userName);
        if (lastMessage==null || lastMessage.equals("default")){
            myHolder.lastMessageTv.setVisibility(View.GONE);
        }
        else {
            myHolder.lastMessageTv.setVisibility(View.VISIBLE);
            myHolder.lastMessageTv.setText(lastMessage);

        }
        try {
            Picasso.get().load(userImage).placeholder(R.drawable.ic_default_img).into(myHolder.profileIv);
        }
        catch (Exception e){
            Picasso.get().load(R.drawable.ic_default_img).into(myHolder.profileIv);
        }
        //set online
        if (userList.get(i).getOnlineStatus().equals("online")){
            //online
            myHolder.onlineStatusIv.setImageResource(R.drawable.circle_online);
        }
        else {
            //offline
            myHolder.onlineStatusIv.setImageResource(R.drawable.circle_offline);
        }

        //handle click of user in chatlist
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start setoran activity with that user
                Intent intent = new Intent(context, PenilaianActivity.class);
                intent.putExtra("hisUid", hisUid);
                context.startActivity(intent);

            }
        });
    }

    public void setLastMessageMap(String userId, String lastMessage){
        lastMessageMap.put(userId, lastMessage);
    }

    @Override
    public int getItemCount() {
        return userList.size(); //size of the list
    }


    class MyHolder extends RecyclerView.ViewHolder{
        //views of row_setoranlist.xml
        ImageView profileIv, onlineStatusIv;
        TextView nameTv, lastMessageTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            profileIv = itemView.findViewById(R.id.profileIv);
            onlineStatusIv = itemView.findViewById(R.id.onlineStatusIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            lastMessageTv = itemView.findViewById(R.id.lastMessageTv);


        }
    }
}
