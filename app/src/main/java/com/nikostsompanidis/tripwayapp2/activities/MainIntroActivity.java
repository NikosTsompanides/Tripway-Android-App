package com.nikostsompanidis.tripwayapp2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;


import com.nikostsompanidis.tripwayapp2.R;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class MainIntroActivity extends MaterialIntroActivity {

    private static final int REQUEST_CODE_INTRO =1 ;
    private Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //enableLastSlideAlphaExitTransition(true);

        setSkipButtonVisible();
        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorGreen)
                        .buttonsColor(R.color.colorPrimary)
                        .image(R.drawable.tripwayfooter)
                        .title(getResources().getString(R.string.title_intro))
                        .description((getResources().getString(R.string.description_intro)))
                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorYellow)
                .buttonsColor(R.color.colorPrimary)
                .image(R.drawable.trip)
                .title(getResources().getString(R.string.second_slide_title_intro))
                .description(getResources().getString(R.string.second_slide_description_intro))
                .build());
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorBlue)
                        .buttonsColor(R.color.colorPrimary)
                        .image(R.drawable.booking)
                        .title(getResources().getString(R.string.third_slide_title_intro))
                        .description(getResources().getString(R.string.third_slide_description_intro))
                        .build());
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorOrange)
                .buttonsColor(R.color.colorPrimary)
                .image(R.drawable.material_people)
                .title(getResources().getString(R.string.fourth_slide_title_intro))
                .description(getResources().getString(R.string.fourth_slide_description_intro))
                .build());
    }
    @Override
    public void onFinish() {
        super.onFinish();
        startActivity(new Intent(ctx,MainActivity.class));
    }
}
