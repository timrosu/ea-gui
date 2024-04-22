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
import com.timrosu.ea_gui.api.tasks.GradeTask;
import com.timrosu.ea_gui.cache.Data;
import com.timrosu.ea_gui.ui.GradeAdapter;

//import com.timrosu.ea_gui.ui.GradeAdapter;

public class GradesFragment extends Fragment {
    private RecyclerView recyclerView;
    private GradeAdapter adapter;

    public GradesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Data.getGradeItems() != null && !Data.getGradeItems().isEmpty()) {
            loadDataList(view);
        } else {
            new Auth(view.getContext()); //pridobi avtentikacijsko kodo, ce se ni nastavljena
            new GradeTask().execute(); //pridobi podatke za prikaz
            waitForResponse(view);
        }

//        gradeRecyclerView = view.findViewById(R.id.gradeRecycler);
//        gradeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
    }

    private void loadDataList(View view) { //nalozi podatke v fragment
        //povezi se na RecyclerView
        recyclerView = view.findViewById(R.id.gradeRecycler);
        adapter = new GradeAdapter();

        RecyclerView.LayoutManager gradeViewManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(gradeViewManager);

        recyclerView.setAdapter(adapter);
    }

    private void waitForResponse(View view) { //caka na odziv in nato zacne z nalaganjem podatkov
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.getGradeItems() != null && !Data.getGradeItems().isEmpty()) {
                    loadDataList(view);
                } else {
                    hand.postDelayed((Runnable) this, 100);
                }
            }
        });
    }

}
