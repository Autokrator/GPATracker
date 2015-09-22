package com.nretsew.gpatracker;

import android.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView semRecyclerView;
    private RecyclerView.Adapter semAdapter;
    private FloatingActionButton addButton;
    private RecyclerView.LayoutManager recyclerLayout;
    private LinearLayout mainLinearLayout;
    List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear);
        semRecyclerView = (RecyclerView) findViewById(R.id.sem_recycler_view);
        semRecyclerView.setHasFixedSize(false);

        recyclerLayout = new LinearLayoutManager(this);
        semRecyclerView.setLayoutManager(recyclerLayout);

        names = new ArrayList<String>();

        for(int i = 0; i < 5; i++){
            names.add("Semester " + i);
        }


        semAdapter = new CustomAdapter(names);
        semRecyclerView.setAdapter(semAdapter);

        addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names.add("NEW SEMESTER ADDED");
                semAdapter.notifyItemInserted(semAdapter.getItemCount());
                semRecyclerView.scrollToPosition(semAdapter.getItemCount() - 1);

            }
        });

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(semRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    names.remove(position);
                                    semAdapter.notifyItemRemoved(position);
                                }
                                semAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    names.remove(position);
                                    semAdapter.notifyItemRemoved(position);
                                }
                                semAdapter.notifyDataSetChanged();
                            }
                        });

        semRecyclerView.addOnItemTouchListener(swipeTouchListener);

    }

}