package com.timrosu.ea_gui.api.responses.wrappers;

import com.google.gson.annotations.SerializedName;
import com.timrosu.ea_gui.api.responses.AbsenceItem;

import java.util.List;

// zdruzi posamezne lastnosti odsotnosti v seznam
public class AbsenceWrapper {
    @SerializedName("items")
    private List<AbsenceItem> items;

    public List<AbsenceItem> getItems() {
        return items;
    }

    public void setItems(List<AbsenceItem> items) {
        this.items = items;
    }
}
