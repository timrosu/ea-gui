package com.timrosu.ea_gui.api.model.response;

import com.google.gson.annotations.SerializedName;

public class ChildResponse {
    @SerializedName("display_name")
    private String name;
    @SerializedName("age")
    private String age;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age_level")
    private String school_year;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("plus_enabled")
    private String plus_enabled;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSchool_year() {
        return school_year;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPlus_enabled() {
        return plus_enabled;
    }
}
