package com.timrosu.ea_gui.ui;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timrosu.ea_gui.R;
import com.timrosu.ea_gui.cache.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.CustomViewHolder> {

    public GradeAdapter() {
        waitForData();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView value, type, date, course;

        public CustomViewHolder(View view) {
            super(view);
            value = view.findViewById(R.id.tv_grade_value);
            type = view.findViewById(R.id.tv_grade_type);
            date = view.findViewById(R.id.tv_grade_date);
            course = view.findViewById(R.id.tv_grade_course);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grade_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.value.setText(Data.getGradeItems().get(position).getGrade());
        holder.type.setText(Data.getGradeItems().get(position).getType_name());
        holder.date.setText(Data.getGradeItems().get(position).getDate());
        holder.course.setText(Data.getGradeItems().get(position).getCourse());
    }

    @Override
    public int getItemCount() {
        if (Data.getGradeItems() == null || Data.getGradeItems().isEmpty()) {
            final CountDownLatch latch = new CountDownLatch(1);
            Handler hand = new Handler(Looper.getMainLooper());
            hand.post(new Runnable() {
                @Override
                public void run() {
                    if (Data.getGradeItems() == null || Data.getGradeItems().isEmpty()) {
                        hand.postDelayed(this, 10);
                    } else {
                        latch.countDown(); // Signal that the data is available
                    }
                }
            });
            try {
                // Wait for the data to be available or timeout after 5 seconds
                latch.await(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // Handle interruption
                Thread.currentThread().interrupt();
            }
            // Return the size of the grade items, or 0 if the data is not available
        }
        return Data.getGradeItems().size();
    }

    private void waitForData() {
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.getGradeItems() == null || Data.getGradeItems().isEmpty()) {
                    hand.postDelayed((Runnable) this, 10);
                }
            }
        });
    }
}

