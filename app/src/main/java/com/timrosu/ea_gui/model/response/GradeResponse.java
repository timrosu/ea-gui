package com.timrosu.ea_gui.model.response;

import com.google.gson.annotations.SerializedName;

public class GradeResponse {
    @SerializedName("course")
    private String course;
    @SerializedName("type_name")
    private String type_name;
    @SerializedName("date")
    private String date;
    @SerializedName("grade")
    private String grade;
    @SerializedName("subject")
    private String subject;

    public String getCourse() {
        return course;
    }

    public String getType_name() {
        return type_name;
    }

    public String getDate() {
        return date;
    }

    public String getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }
}
