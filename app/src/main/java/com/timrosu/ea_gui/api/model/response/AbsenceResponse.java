package com.timrosu.ea_gui.api.model.response;

import com.timrosu.ea_gui.api.model.response.items.AbsenceItem;

import java.util.List;

public class AbsenceResponse {
    private List<AbsenceItem> items;

    public List<AbsenceItem> getItems() {
        return items;
    }

    public void setItems(List<AbsenceItem> items) {
        this.items = items;
    }
}
