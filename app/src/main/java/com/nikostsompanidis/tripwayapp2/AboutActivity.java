package com.nikostsompanidis.tripwayapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

public class AboutActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Context ctx = this;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ctx,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_map:
                    Intent i = new Intent(ctx, MapsActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_about_us:
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) layout.getLayoutParams();


        AboutView view = AboutBuilder.with(this)
                .setPhoto(R.drawable.tripway_app_image)
                .setCover(R.mipmap.profile_cover)
                .setName("Tripway.gr")
                .setSubTitle("We create Memories")
                .setBrief(getResources().getString(R.string.about_brief))
                .setAppIcon(R.drawable.tripway_app_image)
                .setAppName(R.string.app_name)
                .addWebsiteLink("https://www.tripway.gr/")
                .addFacebookLink("tripwayofficial")
                .addLinkedInLink("")
                .addInstagramLink("tripwayofficial")
                .addFeedbackAction("nikostsompanides@gmail.com")
                .addTwitterLink("Tripway17")
                .addIntroduceAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(ctx)
                                .setTitle("Terms and conditions")
                                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        finishAffinity();
                                    }
                                })
                                .setMessage(getResources().getString(R.string.about_brief))
                                .show();
                    }
                })
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();

        addContentView(view, layoutParams);


    }

}
