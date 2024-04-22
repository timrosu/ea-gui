package com.timrosu.ea_gui.api.model.response.items;

public class AbsenceItem {
    private String date;
    private int excused_count;
    private int missing_count;
    private String state;
    private String excuse_description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getExcused_count() {
        return excused_count;
    }

    public void setExcused_count(int excused_count) {
        this.excused_count = excused_count;
    }

    public int getMissing_count() {
        return missing_count;
    }

    public void setMissing_count(int missing_count) {
        this.missing_count = missing_count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExcuse_description() {
        return excuse_description;
    }

    public void setExcuse_description(String excuse_description) {
        this.excuse_description = excuse_description;
    }
}
