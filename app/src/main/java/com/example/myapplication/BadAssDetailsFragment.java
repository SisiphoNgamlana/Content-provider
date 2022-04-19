package com.example.myapplication;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.BadAss;

import java.io.Serializable;

public class BadAssDetailsFragment extends Fragment {

    private static final String BUNDLE_EXTRA_BADASS = "BUNDLE_EXTRA_BADASS";
    private BadAss badAssBundle;

    static BadAssDetailsFragment getInstance(BadAss badAss) {
        BadAssDetailsFragment badAssDetailsFragment = new BadAssDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_EXTRA_BADASS, badAss);
        badAssDetailsFragment.setArguments(bundle);
        return badAssDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bad_ass_details_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            badAssBundle = (BadAss) getArguments().getSerializable(BUNDLE_EXTRA_BADASS);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView badAssName = view.findViewById(R.id.badass_name_edittext);
        ImageView badAssImageDisplay = view.findViewById(R.id.badass_image_imageView);
        Button buttonUpdateBadassDetails = view.findViewById(R.id.button_update_badass_details);
        badAssName.setText(badAssBundle.getName());
        badAssImageDisplay.setImageResource(badAssBundle.getImage());
        buttonUpdateBadassDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO UPDATE connect to content provider
            }
        });
    }
}