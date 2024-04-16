package com.timrosu.ea_gui.api.responses.wrappers;

import com.google.gson.annotations.SerializedName;
import com.timrosu.ea_gui.api.responses.ExamItem;

import java.util.List;

// zdruzi posamezne lastnosti napovedanega ocenjevanja v seznam
public class ExamWrapper {
    @SerializedName("items")
    private List<ExamItem> items;

    public List<ExamItem> getItems() {
        return items;
    }

    public void setItems(List<ExamItem> items) {
        this.items = items;
    }
}
