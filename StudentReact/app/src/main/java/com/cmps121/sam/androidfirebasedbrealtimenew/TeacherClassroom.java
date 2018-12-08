package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherClassroom extends AppCompatActivity {
    RecyclerView recyclerView;

    //Firebase declerations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button delete;
    TextView deleteTitle;

    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post,MyRecyclerViewHolder> adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classroom);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_two);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayComment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayComment();
    }



    protected void onStop(){
        if(adapter != null){
            adapter.stopListening();
        }
        super.onStop();
    }
    private void displayComment(){
        options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(databaseReference, Post.class).build();

        adapter = new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position, @NonNull Post model) {
                holder.txt_title.setText(model.getTitle());
                holder.txt_comment.setText(model.getContent());

                final String tempTitle = holder.txt_title.getText().toString();

                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.getRef(position).removeValue();
                        Toast.makeText(TeacherClassroom.this,"Question deleted",Toast.LENGTH_SHORT).show();
                        displayComment();
                    }
                });
            }



            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.teacher_post_item, viewGroup, false);
                return new MyRecyclerViewHolder(itemView);
            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.UpdateBtn){
            displayComment();
            Toast.makeText(TeacherClassroom.this,"Updated",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.ClearAllBtn) {
            databaseReference.removeValue();
            Toast.makeText(TeacherClassroom.this,"All Cleared",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
