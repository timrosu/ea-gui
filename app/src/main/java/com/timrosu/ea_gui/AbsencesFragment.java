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
import com.timrosu.ea_gui.api.tasks.AbsenceTask;
import com.timrosu.ea_gui.cache.Data;
import com.timrosu.ea_gui.ui.AbsenceAdapter;

public class AbsencesFragment extends Fragment {
    private RecyclerView recyclerView;
    private AbsenceAdapter adapter;
    public AbsencesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_absences,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Data.getAbsenceItems() != null && !Data.getAbsenceItems().isEmpty()) {
            loadDataList(view);
        } else {
            new Auth(view.getContext()); //pridobi avtentikacijsko kodo, ce se ni nastavljena
            new AbsenceTask().execute(); //pridobi podatke za prikaz
            waitForResponse(view);
        }
    }

    private void loadDataList(View view) { //nalozi podatke v fragment
        //povezi se na RecyclerView
        recyclerView = view.findViewById(R.id.absenceRecycler);
        adapter = new AbsenceAdapter();

        RecyclerView.LayoutManager absenceViewManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(absenceViewManager);

        recyclerView.setAdapter(adapter);
    }

    private void waitForResponse(View view) { //caka na odziv in nato zacne z nalaganjem podatkov
        Handler hand = new Handler(Looper.getMainLooper());
        hand.post(new Runnable() {
            @Override
            public void run() {
                if (Data.getAbsenceItems() != null && !Data.getAbsenceItems().isEmpty()) {
                    loadDataList(view);
                } else {
                    hand.postDelayed((Runnable) this, 100);
                }
            }
        });
    }
}
