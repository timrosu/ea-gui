package com.timrosu.ea_gui.api.model.response;

import com.timrosu.ea_gui.api.model.response.items.GradeItem;

import java.util.List;

public class GradeResponse {
    private List<GradeItem> items;

    public List<GradeItem> getItems() {
        return items;
    }

    public void setItems(List<GradeItem> items) {
        this.items = items;
    }

}
