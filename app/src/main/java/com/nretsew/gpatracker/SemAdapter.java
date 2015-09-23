package com.nretsew.gpatracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SemAdapter extends RecyclerView.Adapter<SemAdapter.CardViewHolder> {
    private List<Semester> semesters;
    public FragmentManager fragmentManager;

    public SemAdapter(List<Semester> semesters, FragmentManager fragmentManager) {
        super();
        this.semesters = semesters;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.sem_card_list, parent, false);
        cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CourseViewFragment fr = new CourseViewFragment(semesters.get(0).courses);

                fragmentTransaction.add(R.id.main_layout, fr);
                fragmentTransaction.commit();
            }
        });
        return new CardViewHolder(cardItem);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.title.setText(semesters.get(position).title);
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }



    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public CardViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
        }
    }
}
