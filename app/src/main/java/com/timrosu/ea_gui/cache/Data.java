package com.timrosu.ea_gui.cache;

import com.timrosu.ea_gui.api.model.response.items.AbsenceItem;
import com.timrosu.ea_gui.api.model.response.items.ExamItem;
import com.timrosu.ea_gui.api.model.response.items.GradeItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data { // "predpomnilnik" za vsebino izpisa
    private static List<GradeItem> gradeItems;
    public static List<GradeItem> getGradeItems() {
        return gradeItems;
    }
    public static void setGradeItems(List<GradeItem> gradeItems) {
        Data.gradeItems = gradeItems;
    }

    private static List<ExamItem> examItems;

    public static List<ExamItem> getExamItems() {
        return examItems;
    }

    public static void setExamItems(List<ExamItem> examItems) {
        Data.examItems = examItems;
    }

    private static List<AbsenceItem> absenceItems;
    public static List<AbsenceItem> getAbsenceItems() {
        return absenceItems;
    }

    public static void setAbsenceItems(List<AbsenceItem> absenceItems) {
        Data.absenceItems = absenceItems;
    }

    private static List<ExamItem> examList;
    public static List<ExamItem> getExamList() {
        return examList;
    }

    public static void setExamList(List<ExamItem> examList) {
        Data.examList = examList;
    }

    public static Map<String,String> childMap = new HashMap<>();
}

