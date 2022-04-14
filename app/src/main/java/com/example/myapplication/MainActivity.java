package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String BAD_ASS_FRAGMENT_TAG = "BAD_ASS_FRAGMENT_TAG";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.new_badass_menu_item) {
            Intent intent = new Intent(getApplicationContext(), AddNewBadAssActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListFragment badAssListFragment = new ListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_container, badAssListFragment, BAD_ASS_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}