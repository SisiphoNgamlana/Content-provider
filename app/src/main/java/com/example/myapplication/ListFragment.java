package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.data.BadAssContentProvider;
import com.example.myapplication.model.BadAss;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ListFragment extends Fragment {

    private static final String TAG = ListFragment.class.getName();
    private static final String EXTRA_BADASS = "EXTRA_BADASS";
    private static BadAssRecyclerViewAdapter badAssRecyclerViewAdapter;
    private static RecyclerView badAssItemsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GetListOfBadass getListOfBadass = new GetListOfBadass(getContext());
        getListOfBadass.execute();
        return inflater.inflate(R.layout.fragment_list_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        badAssItemsRecyclerView = view.findViewById(R.id.recycler_view_list_items);
        badAssItemsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        Button buttonDeleteAll = view.findViewById(R.id.button_delete_all);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAllBadass deleteAllBadass = new DeleteAllBadass(getContext());
                deleteAllBadass.execute();
                GetListOfBadass getListOfBadass = new GetListOfBadass(getContext());
                getListOfBadass.execute();
            }
        });
    }

    private List<BadAss> createBadAssList(View view) {
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

    private static class GetListOfBadass extends AsyncTask<Void, Void, List<BadAss>> {

        private final WeakReference<Context> weakReferenceContext;

        public GetListOfBadass(Context context) {
            this.weakReferenceContext = new WeakReference<>(context);
        }

        @Override
        protected List<BadAss> doInBackground(Void... voids) {
            final List<BadAss> listOfBadAssFromProvider = new ArrayList<>();
            Log.d(TAG, "Reading from the DB list");
            try {
                try (Cursor cursor = weakReferenceContext.get().getContentResolver()
                        .query(Uri.parse(String.valueOf(BadAssContentProvider.CONTENT_URI)),
                                null,
                                null,
                                null)) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        BadAss badAss = new BadAss();
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(BadAssContentProvider.NAME));
                        @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(BadAssContentProvider.ID));
                        badAss.setId(Integer.parseInt(id));
                        badAss.setName(name);
                        badAss.setImage(android.R.drawable.picture_frame);
                        listOfBadAssFromProvider.add(badAss);
                        Log.d(TAG, String.format("Badass id: %s", id));
                        cursor.moveToNext();
                    }
                }
            } catch (Exception e) {
                Log.i(TAG, "Something went wrong");
                e.printStackTrace();
            }

            Log.d(TAG, String.valueOf(listOfBadAssFromProvider.size()));
            //TODO this isn't necessary then
            return listOfBadAssFromProvider;
        }

        @Override
        protected void onPostExecute(List<BadAss> badAsses) {
            super.onPostExecute(badAsses);
            setUpRecyclerView(weakReferenceContext.get(), badAsses);
        }
    }

    private static void setUpRecyclerView(Context context, List<BadAss> badAsses) {
        if (badAssItemsRecyclerView.getAdapter() != null) {
            badAssRecyclerViewAdapter.clear(badAsses);
            badAssRecyclerViewAdapter.notifyItemRangeRemoved(0, badAsses.size());

            return;
        }
        badAssRecyclerViewAdapter = new BadAssRecyclerViewAdapter(badAsses);
        badAssItemsRecyclerView.setAdapter(badAssRecyclerViewAdapter);
        badAssRecyclerViewAdapter.setOnClickBadAssItemListener(new BadAssRecyclerViewAdapter.onClickBadAssItemListener() {
            @Override
            public void onBadAssItemClick(View badassItemView, int position) {
                BadAss clickedBadAss = badAsses.get(position);
                //TODO pass to the next activity
                Log.d(TAG, "Selected badass is " + clickedBadAss.getName());
                Intent intent = new Intent(context, BadAssDetailsActivity.class);
                intent.putExtra(EXTRA_BADASS, clickedBadAss);
                context.startActivity(intent);
            }
        });
        badAssRecyclerViewAdapter.setOnLongClickBadAssItemListener(new BadAssRecyclerViewAdapter.OnLongClickBadAssItemListener() {
            @Override
            public void onLongClickBadAssItemListener(View badassItemView, int position) {
                //TODO : Pop up dilog for deleting
                BadAss clickedBadAss = badAsses.get(position);
                DeleteBadass deleteBadass = new DeleteBadass(badassItemView.getContext(), clickedBadAss.getId());
                deleteBadass.execute();
            }
        });
    }

    private static void updateDataSet(Context context, int position) {
        GetListOfBadass getListOfBadass = new GetListOfBadass(context);
        getListOfBadass.execute();
        badAssRecyclerViewAdapter.notifyItemRemoved(position);
    }

    private static class DeleteBadass extends AsyncTask<Integer, Integer, Integer> {

        private final WeakReference<Context> weakReferenceContext;
        private final int position;

        public DeleteBadass(Context context, int position) {
            this.weakReferenceContext = new WeakReference<>(context);
            this.position = position;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            String slashStringValue = String.format("/%s", position);
            //TODO : get value from here integers
             Integer result = weakReferenceContext.get().getContentResolver()
//                    .delete(BadAssContentProvider.CONTENT_URI, null);
                    .delete(Uri.parse(BadAssContentProvider.CONTENT_URI + slashStringValue),null);
            Log.d(TAG, String.format("Delete position %s", position));
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            //TODO : Check the successful int here.
            if (result != 0) {
                Toast.makeText(weakReferenceContext.get(), String.format("Removed item in position: %s", position),
                        Toast.LENGTH_LONG).show();
                GetListOfBadass getListOfBadass = new GetListOfBadass(weakReferenceContext.get());
                getListOfBadass.execute();

            } else {
                Toast.makeText(weakReferenceContext.get(), "Delete failed",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private static class DeleteAllBadass extends AsyncTask<Integer, Integer, Integer> {

        private final WeakReference<Context> weakReferenceContext;
        public DeleteAllBadass(Context context) {
            this.weakReferenceContext = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return weakReferenceContext.get().getContentResolver()
                    .delete(BadAssContentProvider.CONTENT_URI, null);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            //TODO : Check the successful int here.
            if (result != 0) {
                Toast.makeText(weakReferenceContext.get(), String.format("Badasses cleared"),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(weakReferenceContext.get(), "Delete failed",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
