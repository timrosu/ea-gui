package com.timrosu.ea_gui.api.model.response;

import com.timrosu.ea_gui.api.model.response.items.ExamItem;

import java.util.List;

public class ExamResponse {
        private List<ExamItem> items;

        public List<ExamItem> getItems() {
            return items;
        }

        public void setItems(List<ExamItem> items) {
            this.items = items;
        }
}
