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

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.CustomViewHolder> {

    public ExamAdapter() {
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
        View view = layoutInflater.inflate(R.layout.exam_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.value.setText(Data.getExamItems().get(position).getPeriod());
        holder.type.setText(Data.getExamItems().get(position).getTypeName());
        holder.date.setText(Data.getExamItems().get(position).getDate());
        holder.course.setText(Data.getExamItems().get(position).getCourse());
    }

    @Override
    public int getItemCount() {
        if (Data.getExamItems() == null || Data.getExamItems().isEmpty()) {
            final CountDownLatch latch = new CountDownLatch(1);
            Handler hand = new Handler(Looper.getMainLooper());
            hand.post(new Runnable() {
                @Override
                public void run() {
                    if (Data.getExamItems() == null || Data.getExamItems().isEmpty()) {
                        hand.postDelayed(this, 10);
                    } else {
                        latch.countDown(); // Signal that the data is available
                    }
                }
            });
            try {
                latch.await(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // Handle interruption
                Thread.currentThread().interrupt();
            }
        }
        return Data.getExamItems().size();
    }

    private void waitForData() {
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.getExamItems() == null || Data.getExamItems().isEmpty()) {
                    hand.postDelayed((Runnable) this, 10);
                }
            }
        });
    }
}

