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

public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.CustomViewHolder> {

    public AbsenceAdapter() {
        waitForData();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView date, state;

        public CustomViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.tv_abs_date);
            state = view.findViewById(R.id.tv_abs_state);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.absence_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.date.setText(Data.getAbsenceItems().get(position).getDate());
        holder.state.setText(Data.getAbsenceItems().get(position).getState());
    }

    @Override
    public int getItemCount() {
        if (Data.getAbsenceItems() == null || Data.getAbsenceItems().isEmpty()) {
            final CountDownLatch latch = new CountDownLatch(1);
            Handler hand = new Handler(Looper.getMainLooper());
            hand.post(new Runnable() {
                @Override
                public void run() {
                    if (Data.getAbsenceItems() == null || Data.getAbsenceItems().isEmpty()) {
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
        return Data.getAbsenceItems().size();
    }

    private void waitForData() {
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.getAbsenceItems() == null || Data.getAbsenceItems().isEmpty()) {
                    hand.postDelayed((Runnable) this, 10);
                }
            }
        });
    }
}

