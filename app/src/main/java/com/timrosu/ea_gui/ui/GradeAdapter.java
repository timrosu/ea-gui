package com.timrosu.ea_gui.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timrosu.ea_gui.R;
import com.timrosu.ea_gui.api.Api;
import com.timrosu.ea_gui.api.callback.GradeCallback;
import com.timrosu.ea_gui.model.response.GradeResponse;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    Api api;
    Context context;
    private final List<GradeResponse> gradeResponseList;
    public GradeAdapter(List<GradeResponse> gradeResponseList){
        this.gradeResponseList = gradeResponseList;
    }

    class GradeViewHolder extends RecyclerView.ViewHolder {
        public TextView value,type,date,subject;

        public GradeViewHolder(View view) {
            super(view);
            value = view.findViewById(R.id.tv_grade_value);
            type = view.findViewById(R.id.tv_grade_type);
            date = view.findViewById(R.id.tv_grade_date);
            subject = view.findViewById(R.id.tv_grade_subject);
        }
    }

    public GradeAdapter(List<GradeResponse> gradeResponseList, Context context) {
        this.context = context;
        this.gradeResponseList = gradeResponseList;
        api = new Api(context);
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_grade_item, parent, false);
        return new GradeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        GradeResponse gradeResponse = api.getGrades(new GradeCallback() {
            @Override
            public void onFetchSuccess(List<GradeResponse> gradeResponseList) {

            }

            @Override
            public void onFetchError(Throwable throwable) {

            }
        }).get(position);


        holder.value.setText(gradeResponse.getGrade());
        holder.type.setText(gradeResponse.getType_name());
        holder.date.setText(gradeResponse.getDate());
        holder.subject.setText(gradeResponse.getSubject());
    }

    @Override
    public int getItemCount() {
        return gradeResponseList.size();
    }
}

