/*
* created by Meera Mali, 2015.
*
*/

package com.spacesociety;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.spacesociety.utils.FileDownloader;

import java.io.File;
import java.io.IOException;

public class PdfLibrary extends ListActivity {

    private static final String TITLES[] = {"ARLISS Experimental Rocketry Project with Ken Biba," +
            "May 13, 2015",
            "Mining the Moon with a Lunar Elevator with Charles Radley, May 2, 2015",
            "A Lunar Volatiles Miner",
            " Jacob's Ladder: A Lunar Space Elevator",
            "Lunar Space Elevators for Cislunar Space Development", "Data Privacy and Security in Space presentation", "3D Printing for Lunar and Free-Space Colonization",
            "The Developing Space Economy for Commercial Space and Entrepreneurship",
            "Data Science in the Space Age", "STEAM into Space! A Panel of Space Teachers presentation"
    };
    private static final String URL_LIST[] = {"http://www.spacesociety-sv.org/files/104233972.pdf",
            "http://www.spacesociety-sv.org/files/103569018.pdf",
            "http://www.spacesociety-sv.org/files/103752465.pdf",
            "http://www.spacesociety-sv.org/files/103752461.pdf",
            "http://www.spacesociety-sv.org/files/103752463.pdf",
            "http://www.spacesociety-sv.org/files/102723967.pdf",
            "http://www.spacesociety-sv.org/files/101999975.pdf",
            "http://www.spacesociety-sv.org/files/101821132.pdf",
            "http://www.spacesociety-sv.org/files/100720699.pdf",
            "http://www.spacesociety-sv.org/files/100109545.pdf"
    };
    private static final String FOLDER_LIB = "/SpaceSociety/";
    private static final String FOLDER_NAME = "SpaceSociety";
    private static final String PDF_APP = "application/pdf";
    private static final String ERROR = "No Application Available to View PDF";
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
            File file = new File(Environment.getExternalStorageDirectory() + FOLDER_LIB + fileName);
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, FOLDER_NAME);
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile, mContext);
            readPdf(fileName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.dismiss();
        }

    }

    protected void readPdf(String fileName) {

        File file = new File(Environment.getExternalStorageDirectory() + FOLDER_LIB + fileName);
        if (file.exists()) {
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, PDF_APP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(PdfLibrary.this, ERROR,
                        Toast.LENGTH_SHORT).show();
            }
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
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.pdf_library, TITLES));
        mListView = getListView();
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File(Environment.getExternalStorageDirectory() + FOLDER_LIB + TITLES[position] + ".pdf");
                if (!(file.exists())) {
                    if (netCheck()) Toast.makeText(PdfLibrary.this, NET_ERROR,
                            Toast.LENGTH_SHORT).show();
                    else
                        new DownloadFile(PdfLibrary.this).execute(URL_LIST[position], TITLES[position] + ".pdf");
                } else readPdf(TITLES[position] + ".pdf");


            }
        });
    }

}
