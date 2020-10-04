package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelperFeedback {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceFeedback;
    private List<Feedback> feedbacks = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Feedback> feedbacks, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();


    }


    public FirebaseDatabaseHelperFeedback() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceFeedback = mDatabase.getReference("Feedback");

    }





    public void readFeedback(final DataStatus dataStatus) {
        mReferenceFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbacks.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Feedback feedback = keyNode.getValue(Feedback.class);
                    feedbacks.add(feedback);
                }

                dataStatus.DataIsLoaded(feedbacks, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateFeedback(String key, Feedback feedback, final DataStatus dataStatus) {
        mReferenceFeedback.child(key).setValue(feedback).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteFeedback(String key, final DataStatus dataStatus) {
        mReferenceFeedback.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });

    }
}

