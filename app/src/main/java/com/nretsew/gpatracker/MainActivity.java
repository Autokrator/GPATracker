package com.nretsew.gpatracker;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private RecyclerView semRecyclerView;
    private RecyclerView.Adapter semAdapter;
    List<Semester> semesters;

    private CourseViewFragment courseViewFragment;
    List<Course> courses;

    private FloatingActionButton addButton;
    private RecyclerView.LayoutManager recyclerLayout;
    private FrameLayout mainFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSem();
    }

    private void initSem(){
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_layout);
        semRecyclerView = (RecyclerView) findViewById(R.id.sem_recycler_view);
        semRecyclerView.setHasFixedSize(false);

        recyclerLayout = new LinearLayoutManager(this);
        semRecyclerView.setLayoutManager(recyclerLayout);

        semesters = new ArrayList<Semester>();

        for(int i = 0; i < 2; i++){
            semesters.add(new Semester("Semester " + (i+1), generateCourses()));
        }

        semAdapter = new SemAdapter(semesters, getFragmentManager());
        semRecyclerView.setAdapter(semAdapter);

        addButton = (FloatingActionButton) findViewById(R.id.button_add_sem);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semesters.add(new Semester("NEW SEMESTER ADDED", generateCourses()));
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
                                    semesters.remove(position);
                                    semAdapter.notifyItemRemoved(position);
                                }
                                semAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    semesters.remove(position);
                                    semAdapter.notifyItemRemoved(position);
                                }
                                semAdapter.notifyDataSetChanged();
                            }
                        });

        semRecyclerView.addOnItemTouchListener(swipeTouchListener);
        semRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int view_location[] = new int[2];
                view.getLocationOnScreen(view_location);
                FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                View card = LayoutInflater.from(view.getContext()).inflate(R.layout.sem_card_list, mainFrameLayout, false);
                card.setLayoutParams(parms);
            }
        });

    }

    public List<Course> generateCourses() {
        courses = new ArrayList<Course>();

        // Init: Random Values
        for(int i = 0; i < 5; i++){
            courses.add(new Course("Course " + i, i*0.5, i*10));
        }
        courseViewFragment = new CourseViewFragment(courses);

        return courses;
    }
}
