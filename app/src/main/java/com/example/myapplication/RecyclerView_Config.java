package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {

    private Context mContext;
    private FeedbackAdapter mFeedbackAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Feedback> feedbacks, List<String> keys){
        mContext = context;
        mFeedbackAdapter = new FeedbackAdapter(feedbacks, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mFeedbackAdapter);

    }
    class FeedbackItemView extends RecyclerView.ViewHolder{
        private TextView UserName;
        private TextView feedbackS;


        private String key;
        //constructor
        public FeedbackItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.feedback_list_item, parent, false));

            UserName = (TextView) itemView.findViewById(R.id.title_txtUserName);
            feedbackS =(TextView) itemView.findViewById(R.id.feedbackName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(mContext, Feedback_Details.class);
                    intent.putExtra("key",key);
                    intent.putExtra("name",UserName.getText().toString());
                    intent.putExtra("feedback",feedbackS.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }
        public void bind(Feedback feedback, String key){
            UserName.setText(feedback.getName());
            feedbackS.setText(feedback.getFeedback());

            this.key = key;
        }
    }
    class FeedbackAdapter extends RecyclerView.Adapter<FeedbackItemView>{
        private List<Feedback> mFeedbackList;
        private List<String> mKeys;

        public FeedbackAdapter(List<Feedback> mFeedbackList, List<String> mKeys) {
            this.mFeedbackList = mFeedbackList;
            this.mKeys = mKeys;
        }



        @NonNull
        @Override
        public FeedbackItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FeedbackItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedbackItemView holder, int position) {
            holder.bind(mFeedbackList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mFeedbackList.size();
        }
    }
}

