/*
* created by Meera Mali, 2015.
*
*/

package com.spacesociety;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Mp3Library extends ListActivity {

    private static final String TITLES[] = {"Colonizing Mars with Pioneering Technologies ", "Data Science in the Space Age",
            "Mining the Moon with a Lunar Elevator with Charles Radley, May 2, 2015",
            "3D Printing for Lunar and Free-Space Colonization", "The Developing Space Economy for Commercial Space and Entrepreneurship ",
            "Big Data Analytics in Space Exploration and Entrepreneurship", "SOFIA, NASA's Stratospheric Observatory for Infrared Astronomy",
            "Space Manufacturing System"
    };
    private static final String URL_LIST[] = {"http://www.spacesociety-sv.org/files/105115504.mp3",
            "http://www.spacesociety-sv.org/files/100720692.mp3", "http://www.spacesociety-sv.org/files/103752519.mp3", "http://www.spacesociety-sv.org/files/102173143.mp3", "http://www.spacesociety-sv.org/files/101875179.mp3",
            "http://www.spacesociety-sv.org/files/99581500.mp3", "http://www.spacesociety-sv.org/files/98401509.mp3",
            "http://www.spacesociety-sv.org/files/94763974.mp3"

    };
    private static final String FOLDER_LIB = "/SpaceSociety/";
    private static final String FOLDER_NAME = "SpaceSociety";
    private static final String LOADING_TITLE = "DOWNLOADING";
    private static final String LOADING_MSG = "Please wait, the download might take sometime :)";
    private static final String NET_ERROR = "DEVICE IS NOT CONNECTED TO THE INTERNET";

    private ListView mListView;

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        private Context mContext;
        private ProgressDialog mProgress;

        @Override
        protected void onPreExecute() {
            mProgress = ProgressDialog.show(mContext, LOADING_TITLE, LOADING_MSG, true);

        }

        public DownloadFile(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, FOLDER_NAME);
            folder.mkdir();
            File mp3File = new File(folder, fileName);
            try {
                mp3File.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, mp3File, mContext);
            playMp3(fileName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.dismiss();
        }

    }

    public boolean netCheck() {
        boolean status = true;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = false;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return status;
    }


    protected void playMp3(String fileName) {

        File file = new File(Environment.getExternalStorageDirectory() + FOLDER_LIB + fileName);
        if (file.exists()) {
            Intent intent = new Intent(Mp3Library.this, PlayMp3.class);
            String[] val = new String[2];
            val[0] = FOLDER_LIB;
            val[1] = fileName;
            intent.putExtra("DATA", val);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.mp3_library, TITLES));
        mListView = getListView();
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File file = new File(Environment.getExternalStorageDirectory() + FOLDER_LIB + TITLES[position] + ".mp3");
                if (!(file.exists())) {
                    if (netCheck()) Toast.makeText(Mp3Library.this, NET_ERROR,
                            Toast.LENGTH_SHORT).show();
                    else
                        new DownloadFile(Mp3Library.this).execute(URL_LIST[position], TITLES[position] + ".mp3");
                } else {
                    playMp3(TITLES[position] + ".mp3");
                }
            }
        });
    }


}
