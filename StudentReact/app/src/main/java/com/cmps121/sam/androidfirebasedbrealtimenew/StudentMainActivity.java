package com.cmps121.sam.androidfirebasedbrealtimenew;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StudentMainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav1);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //firebaseDatabase = FirebaseDatabase.getInstane();
        //databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");

        //Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, new HomeStudentFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    android.support.v4.app.Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeStudentFragment();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddStudentFragment();
                            break;
                        case R.id.nav_list:
                            selectedFragment = new ListStudentFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
