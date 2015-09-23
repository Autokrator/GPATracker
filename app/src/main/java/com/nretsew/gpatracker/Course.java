package com.nretsew.gpatracker;

public class Course {
    public String title;
    public String weight;
    public String grade;

    public Course(String title, double weight, int grade) {
        this.title = title;
        this.weight = Double.toString(weight);
        this.grade = Integer.toString(grade);
    }
}
