package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.model.BadAss;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ListFragment extends Fragment {

    private static final String EXTRA_BADASS = "EXTRA_BADASS";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<BadAss> listOfBadAss = createBadAssList();

        BadAssRecyclerViewAdapter badAssRecyclerViewAdapter = new BadAssRecyclerViewAdapter(listOfBadAss);
        RecyclerView BadAssItemsRecyclerView = view.findViewById(R.id.recycler_view_list_items);
        BadAssItemsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        BadAssItemsRecyclerView.setAdapter(badAssRecyclerViewAdapter);
        badAssRecyclerViewAdapter.setOnClickBadAssItemListener(new BadAssRecyclerViewAdapter.onClickBadAssItemListener() {
            @Override
            public void onBadAssItemClick(View badassItemView, int position) {
                BadAss clickedBadAss = listOfBadAss.get(position);
                //TODO pass to the next activity
                Log.d("BadAss", "Selected badass is " + clickedBadAss.getName());
                Intent intent = new Intent(view.getContext(), BadAssDetailsActivity.class);
                intent.putExtra(EXTRA_BADASS, clickedBadAss);
                startActivity(intent);
            }
        });
    }

    private List<BadAss> createBadAssList() {
        List<BadAss> listOfBadAss = new ArrayList<>();
        listOfBadAss.add(new BadAss("Master Chief", R.drawable.image_master_chief));
        listOfBadAss.add(new BadAss("Kratos", R.drawable.image_kratos));
        listOfBadAss.add(new BadAss("Eminem", android.R.drawable.picture_frame));
        listOfBadAss.add(new BadAss("BT-7274", android.R.drawable.picture_frame));
        listOfBadAss.add(new BadAss("Vergil", android.R.drawable.picture_frame));
        listOfBadAss.add(new BadAss("Sephiroth", android.R.drawable.picture_frame));
        listOfBadAss.add(new BadAss("Marcus Fenix", android.R.drawable.picture_frame));
        listOfBadAss.add(new BadAss("John Wick", android.R.drawable.picture_frame));
        listOfBadAss.add(new BadAss("Batman", android.R.drawable.picture_frame));
        return listOfBadAss;
    }


}
