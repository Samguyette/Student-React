package com.cmps121.sam.androidfirebasedbrealtimenew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TeacherLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
    }

    public void gotoMainActivity(View view) {
        Intent gotoMainClassroom = new Intent(TeacherLogin.this, TeacherTopicActivity.class);
        startActivity(gotoMainClassroom);
    }
}
