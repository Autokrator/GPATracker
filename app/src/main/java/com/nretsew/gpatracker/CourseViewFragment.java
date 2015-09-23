package com.nretsew.gpatracker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;

public class CourseViewFragment extends Fragment {

    private RecyclerView courseRecyclerView;
    private RecyclerView.Adapter courseAdapter;
    private List<Course> courses;
    private FloatingActionButton addButton;

    public CourseViewFragment() {

    }

    public CourseViewFragment(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Reference to fragment view.
        View v = inflater.inflate(R.layout.fragment_course_view, container, false);

        courseRecyclerView = (RecyclerView) v.findViewById(R.id.course_recycler_view);
        courseRecyclerView.setHasFixedSize(false);

        courseAdapter = new CourseAdapter(courses);
        courseRecyclerView.setAdapter(courseAdapter);

        addButton = (FloatingActionButton) v.findViewById(R.id.button_add_course);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courses.add(new Course("NEW COURSE ADDED", 0.5, 95));
                courseAdapter.notifyItemInserted(courseAdapter.getItemCount());
                courseRecyclerView.scrollToPosition(courseAdapter.getItemCount() - 1);

            }
        });

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(courseRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    courses.remove(position);
                                    courseAdapter.notifyItemRemoved(position);
                                }
                                courseAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    courses.remove(position);
                                    courseAdapter.notifyItemRemoved(position);
                                }
                                courseAdapter.notifyDataSetChanged();
                            }
                        });

        courseRecyclerView.addOnItemTouchListener(swipeTouchListener);


        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
