package com.spacesociety.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.*;
import android.widget.FrameLayout;
import com.spacesociety.R;
import com.spacesociety.fragments.*;


public class MainActivity extends Activity {

    public MainFragment mainFragment;
    public PdfLibraryFragment pdfLibraryFragment;
    public Mp3LibraryFragment mp3LibraryFragment;
    public WebsiteFragment websiteFragment;

    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (FrameLayout) findViewById(R.id.container);

        //add color to action bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.dark_main_color));
        //hide icon
        getActionBar().setDisplayShowHomeEnabled(false);
        //create  fragments
        mainFragment = new MainFragment();
        pdfLibraryFragment = new PdfLibraryFragment();
        mp3LibraryFragment = new Mp3LibraryFragment();
        websiteFragment = new WebsiteFragment();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_main) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        } else
        if (id == R.id.action_pdf) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, pdfLibraryFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        } else
        if (id == R.id.action_mp3) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mp3LibraryFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        } else
        if (id == R.id.action_website) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, websiteFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
