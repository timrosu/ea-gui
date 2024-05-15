package com.timrosu.ea_gui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.timrosu.ea_gui.api.auth.Auth;
import com.timrosu.ea_gui.api.tasks.ExamTask;
import com.timrosu.ea_gui.cache.Data;
import com.timrosu.ea_gui.ui.ExamAdapter;

public class ExamsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExamAdapter adapter;
    public ExamsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_exams,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Data.getExamItems() != null && !Data.getExamItems().isEmpty()) {
            loadDataList(view);
        } else {
            new Auth(view.getContext()); //pridobi avtentikacijsko kodo, ce se ni nastavljena
            new ExamTask().execute(); //pridobi podatke za prikaz
            waitForResponse(view);
        }
    }

    private void loadDataList(View view) { //nalozi podatke v fragment
        //povezi se na RecyclerView
        recyclerView = view.findViewById(R.id.examRecycler);
        adapter = new ExamAdapter();

        RecyclerView.LayoutManager examViewManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(examViewManager);

        recyclerView.setAdapter(adapter);
    }

    private void waitForResponse(View view) { //caka na odziv in nato zacne z nalaganjem podatkov
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.getExamItems() != null && !Data.getExamItems().isEmpty()) {
                    loadDataList(view);
                } else {
                    hand.postDelayed((Runnable) this, 100);
                }
            }
        });
    }
}
