package com.timrosu.ea_gui.api.responses;

import com.google.gson.annotations.SerializedName;

public class GradeItem {
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
}
