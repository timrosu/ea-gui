package com.timrosu.ea_gui.api.responses;

import com.google.gson.annotations.SerializedName;

public class AbsenceItem {
    @SerializedName("date")
    private String date;
    @SerializedName("excused_count")
    private int excused_count;
    @SerializedName("missing_count")
    private int missing_count;
    @SerializedName("state")
    private String state;
    @SerializedName("excuse_description")
    private String excuse_description;
}
