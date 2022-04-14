package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.model.BadAss;

public class BadAssDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_BADASS = "EXTRA_BADASS";
    private static final String TAG_BADASS_FRAGMENT = "TAG_BADASS_FRAGMENT";

    BadAssDetailsFragment badAssDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_ass_details);

        if (getIntent() == null) {
            onBackPressed();
        }
        BadAss badAss = (BadAss) getIntent().getSerializableExtra(EXTRA_BADASS);
        badAssDetailsFragment = BadAssDetailsFragment.getInstance(badAss);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.badass_details_frame_layout, badAssDetailsFragment, TAG_BADASS_FRAGMENT);
        fragmentTransaction.commit();

    }
}