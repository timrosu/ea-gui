package com.timrosu.ea_gui.api.responses.wrappers;

import com.google.gson.annotations.SerializedName;
import com.timrosu.ea_gui.api.responses.GradeItem;

import java.util.List;

// zdruzi posamezne lastnosti ocene v seznam
public class GradeWrapper {
    @SerializedName("items")
    private List<GradeItem> items;

    public List<GradeItem> getItems() {
        return items;
    }

    public void setItems(List<GradeItem> items) {
        this.items = items;
    }
}
