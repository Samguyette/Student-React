package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder{

    TextView txt_title;
    TextView txt_comment;
    TextView txt_upVoteCount;
    Button deleteBtn;               //myrecyclerview has to be its own for teach and student
    Button upVoteBtn;
    int upVotes;


    public MyRecyclerViewHolder(@NonNull View itemView){
        super(itemView);


        txt_comment = (TextView) itemView.findViewById(R.id.txt_content);
        txt_title = (TextView) itemView.findViewById(R.id.txt_title);
        txt_upVoteCount = (TextView) itemView.findViewById(R.id.txt_upVotes);
        deleteBtn = (Button) itemView.findViewById(R.id.teachDeleteBtn);
        upVoteBtn = (Button) itemView.findViewById(R.id.upBtn);
        upVotes = 1;
    }

}
