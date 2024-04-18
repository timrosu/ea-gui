package com.timrosu.ea_gui.model.response;

import com.google.gson.annotations.SerializedName;

public class ChildResponse {
    @SerializedName("display_name")
    private static String name;
    @SerializedName("age")
    private static String age;
    @SerializedName("gender")
    private static String gender;
    @SerializedName("age_level")
    private static String school_year;
    @SerializedName("id")
    private static String id;
    @SerializedName("type")
    private static String type;
    @SerializedName("plus_enabled")
    private static String plus_enabled;
    @SerializedName("timetable")
    private Timetable timetable;

    public static class Timetable {
        @SerializedName("date")
        private static String date;
    }

    public static String getName() {
        return name;
    }

    public static String getAge() {
        return age;
    }

    public static String getGender() {
        return gender;
    }

    public static String getSchool_year() {
        return school_year;
    }

    public static String getId() {
        return id;
    }

    public static String getType() {
        return type;
    }

    public static String getPlus_enabled() {
        return plus_enabled;
    }

    public static String getDate() {
        return Timetable.date;
    }
}
