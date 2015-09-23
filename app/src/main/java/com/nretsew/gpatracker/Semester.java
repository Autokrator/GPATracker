package com.nretsew.gpatracker;

import java.util.List;

public class Semester {
    public String title;
    List<Course> courses;

    public Semester(String title, List<Course> courses) {
        this.title = title;
        this.courses = courses;
    }
}