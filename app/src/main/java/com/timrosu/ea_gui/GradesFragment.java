package com.timrosu.ea_gui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.timrosu.ea_gui.api.Api;
import com.timrosu.ea_gui.api.callback.GradeCallback;
import com.timrosu.ea_gui.model.response.GradeResponse;
import com.timrosu.ea_gui.ui.GradeAdapter;

import java.util.List;

public class GradesFragment extends Fragment {
    private RecyclerView gradeRecyclerView;
    private GradeAdapter gradeAdapter;

    public GradesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Api api = new Api(getContext()); // Initialize Api here
        api.getGrades(new GradeCallback(){

            @Override
            public void onFetchSuccess(List<GradeResponse> gradeResponseList) {
                gradeAdapter = new GradeAdapter(gradeResponseList, getContext());
                gradeRecyclerView.setAdapter(gradeAdapter);
            }

            @Override
            public void onFetchError(Throwable throwable) {
                Log.d("GradesFragment", "Error fetching grades", throwable);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        

        gradeRecyclerView = view.findViewById(R.id.gradeRecycler);
        gradeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
