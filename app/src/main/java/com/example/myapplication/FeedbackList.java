package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class FeedbackList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_Appo);
        new FirebaseDatabaseHelperFeedback().readFeedback(new FirebaseDatabaseHelperFeedback.DataStatus() {
            @Override
            public void DataIsLoaded(List<Feedback> feedbacks, List<String> keys) {
                findViewById(R.id.loading_appo_progressBar).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView,FeedbackList.this,
                        feedbacks, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}