package com.nikostsompanidis.tripwayapp2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nikostsompanidis.tripwayapp2.R;
import com.nikostsompanidis.tripwayapp2.model.Trip;

public class DescriptionsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String descriptions;
    private Trip trip;

    public DescriptionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            descriptions = getArguments().getString("descriptions");
            trip =(Trip)getArguments().getSerializable("trip");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_descriptions, container, false);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        TextView textView = (TextView) view.findViewById(R.id.descriptionstext);
        textView.setText(Html.fromHtml(descriptions).toString());

        ImageButton imageButton = view .findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, view.getContext().getResources().getString(R.string.share_text)+" : https://tripway.travelotopos.com/s/"+trip.getId()+"/1");
                sendIntent.setType("text/plain");
                view.getContext().startActivity(Intent.createChooser(sendIntent, view.getContext().getResources().getText(R.string.app_name)));

            }
        });
    }
}