package com.example.admin.imagepresentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final int MENU_ITEM_START = 0;
    private final int MENU_ITEM_OPTIONS = 1;

    ATask asyncTask;
    ImageView main_iView;
    SharedPreferences sharedPreferences;
    File path;
    File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        files = path.listFiles();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        main_iView = (ImageView) findViewById(R.id.main_iView);
        main_iView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPresentation();
            }
        });
    }

    private void stopPresentation() {
        if (asyncTask == null) return;
        asyncTask.cancel(false);
        getSupportActionBar().show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPresentation();
    }

    private void startPresentation() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        int delay = Integer.parseInt(sharedPreferences.getString("listPref_seconds", "3"));
        asyncTask = new ATask();
        asyncTask.execute(delay);
    }

    @Override
    protected void onPause() {
        stopPresentation();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        stopPresentation();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ITEM_START, 0, "Слайдшоу");
        MenuItem item = menu.add(0, MENU_ITEM_OPTIONS, 0, "Настройки");
        item.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_START) {
            startPresentation();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ATask extends AsyncTask<Integer, Void, Void> {

        ArrayList<File> images = new ArrayList<>();
        int imageCounter = 0;

        @Override
        protected void onPreExecute() {
            for (File f : files) {
                if (f.isFile()) {
                    images.add(f);
                }
            }
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            while (!asyncTask.isCancelled()) {
                publishProgress();
                try {
                    TimeUnit.SECONDS.sleep(integers[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            if (imageCounter >= images.size()) {
                imageCounter = 0;
            }
            String filePath = images.get(imageCounter++).getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            main_iView.setImageBitmap(bitmap);
        }
    }
}
