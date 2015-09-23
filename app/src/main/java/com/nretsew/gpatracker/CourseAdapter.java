package com.nretsew.gpatracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CardViewHolder> {
    private List<MainActivity.Course> courses;

    public CourseAdapter(List<MainActivity.Course> courses) {
        super();
        this.courses = courses;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_list, parent, false);
        cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new CardViewHolder(cardItem);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.title.setText(courses.get(position).title);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }



    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView weight;
        private TextView grade;

        public CardViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            weight = (TextView) v.findViewById(R.id.weight);
            grade = (TextView) v.findViewById(R.id.grade);
        }
    }
}