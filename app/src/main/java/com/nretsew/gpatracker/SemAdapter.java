package com.nretsew.gpatracker;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
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
                //Get reference to mainactivity and layout
                MainActivity ref_context = (MainActivity) view.getContext();
                FrameLayout ref_frame = (FrameLayout) ref_context.findViewById(R.id.main_layout);

                //Remove floating action button and recycler view
                ((FloatingActionButton)ref_context.findViewById(R.id.button_add_sem)).setVisibility(View.GONE);
                ((RecyclerView)ref_context.findViewById(R.id.sem_recycler_view)).setVisibility(View.GONE);

                //Copy card to create animation with
                View card = LayoutInflater.from(view.getContext()).inflate(R.layout.sem_card_list, ref_frame, false);
                ((TextView) card.findViewById(R.id.title)).setText(((TextView) view.findViewById(R.id.title)).getText());


                DisplayMetrics dm = ref_context.getResources().getDisplayMetrics();
                int px = Math.round(6 * (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                ref_context.getWindowManager().getDefaultDisplay().getMetrics(dm);
                int statusBarOffset = dm.heightPixels - ref_frame.getMeasuredHeight();

                int originalPos[] = new int[2];
                view.getLocationOnScreen(originalPos);

                int yDest = (view.getMeasuredHeight()/2) - statusBarOffset + 2*px;

                card.setY(originalPos[1] - statusBarOffset - px);
                ref_frame.addView(card);

                TranslateAnimation translate = new TranslateAnimation(0,0,0,yDest-originalPos[1]);
                translate.setFillAfter(true);
                translate.setDuration(500);

                final View animatedView = card;
                final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) animatedView.getLayoutParams();
                final int margin = params.topMargin;
                ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    float radius = ((CardView) animatedView).getRadius();

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        params.setMargins((Integer) valueAnimator.getAnimatedValue(),
                                (Integer) valueAnimator.getAnimatedValue(),
                                (Integer) valueAnimator.getAnimatedValue(),
                                (Integer) valueAnimator.getAnimatedValue());
                        ((CardView) animatedView).setRadius(radius * (((Integer) valueAnimator.getAnimatedValue()) / margin));
                        animatedView.requestLayout();
                    }
                });
                animator.setDuration(300);

                card.startAnimation(translate);
                animator.start();


//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                CourseViewFragment fr = new CourseViewFragment(semesters.get(0).courses);
//
//                fragmentTransaction.add(R.id.main_layout, fr);
//                fragmentTransaction.commit();
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



    public static class CardViewHolder extends RecyclerView.ViewHolder{
        private TextView title;

        public CardViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
        }

    }
}
