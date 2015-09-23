package com.nretsew.gpatracker;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView semRecyclerView;
    private RecyclerView.Adapter semAdapter;
    List<String> sems;

    private RecyclerView courseRecyclerView;
    private RecyclerView.Adapter courseAdapter;
    List<Course> courses;

    private FloatingActionButton addButton;
    private RecyclerView.LayoutManager recyclerLayout;
    private LinearLayout mainLinearLayout;
    private CourseViewFragment courseViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSem();
        //initCourse();
        showCourseView();
    }

    private void showCourseView() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_linear, courseViewFragment);
        fragmentTransaction.commit();
    }

    private void initSem(){
        mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear);
        semRecyclerView = (RecyclerView) findViewById(R.id.sem_recycler_view);
        semRecyclerView.setHasFixedSize(false);

        recyclerLayout = new LinearLayoutManager(this);
        semRecyclerView.setLayoutManager(recyclerLayout);

        sems = new ArrayList<String>();

        for(int i = 0; i < 5; i++){
            sems.add("Semester " + i);
        }


        semAdapter = new SemAdapter(sems);
        semRecyclerView.setAdapter(semAdapter);

        addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sems.add("NEW SEMESTER ADDED");
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
                                    sems.remove(position);
                                    semAdapter.notifyItemRemoved(position);
                                }
                                semAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    sems.remove(position);
                                    semAdapter.notifyItemRemoved(position);
                                }
                                semAdapter.notifyDataSetChanged();
                            }
                        });

        semRecyclerView.addOnItemTouchListener(swipeTouchListener);

    }

    public class Course {
        public String title;
        public double weight;
        public int grade;

        public Course(String title, double weight, int grade) {
            this.title = title;
            this.weight = weight;
            this.grade = grade;
        }
    }
}
