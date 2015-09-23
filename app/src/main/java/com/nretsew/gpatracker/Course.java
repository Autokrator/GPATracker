package com.nretsew.gpatracker;

public class Course {
    public String title;
    public String weight;
    public String grade;

    public Course(String title, double weight, int grade) {
        this.title = title;
        this.weight = "Weight " + Double.toString(weight);
        this.grade = "Grade: " + Integer.toString(grade);
    }
}
