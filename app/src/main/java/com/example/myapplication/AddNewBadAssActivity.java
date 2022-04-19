package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.data.BadAssContentProvider;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

public class AddNewBadAssActivity extends AppCompatActivity {

    public static final String TAG = AddNewBadAssActivity.class.getName();

    Button buttonAddNewBadass;
    Button buttonLoadImage;
    AppCompatImageView imageViewBadassImage;
    EditText editTextLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO : Use ConstraintLayout
        setContentView(R.layout.activity_add_new_bad_ass);

        buttonAddNewBadass = findViewById(R.id.button_add_new_badass);
        buttonLoadImage = findViewById(R.id.button_load_image);
        editTextLoadImage = findViewById(R.id.edittext_badass_image);
        imageViewBadassImage = findViewById(R.id.imageview_badass_image);

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveAndLoadImage(view, editTextLoadImage.getText().toString());
            }
        });
        buttonAddNewBadass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AddNewBadassAsyncTask addNewBadassAsyncTask = new AddNewBadassAsyncTask(view.getContext());
                addNewBadassAsyncTask.execute();
            }
        });
    }

    private void retrieveAndLoadImage(View view, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            Toast.makeText(getApplicationContext(), "Please enter image URl", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "Loading Image");
        Picasso.get().load(imageUrl)
                .resize(imageViewBadassImage.getWidth(), imageViewBadassImage.getHeight())
                .centerInside()
                .into(imageViewBadassImage);
    }

    private static class AddNewBadassAsyncTask extends AsyncTask<String, String, String> {

            private final WeakReference<Context> weakReferenceContext;

            public AddNewBadassAsyncTask(Context context) {
                this.weakReferenceContext = new WeakReference<>(context);
            }

        @Override
        protected String doInBackground(String... strings) {
                Log.i("List activity", "reading the db");
            ContentValues contentValues = new ContentValues();
            contentValues.put(BadAssContentProvider.NAME, "Kratos");
            contentValues.put(BadAssContentProvider.IMAGE, 1);
                weakReferenceContext.get().getContentResolver().insert(BadAssContentProvider.CONTENT_URI, contentValues, null);
            return null;
        }
    }
}