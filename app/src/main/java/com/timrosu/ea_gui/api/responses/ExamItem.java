package com.timrosu.ea_gui.api.responses;

import com.google.gson.annotations.SerializedName;

public class ExamItem {
    @SerializedName("course")
    private String course;
    @SerializedName("type_name")
    private String typeName;
    @SerializedName("date")
    private String date;
    @SerializedName("period")
    private String period;
    @SerializedName("subject")
    private String subject;
}
