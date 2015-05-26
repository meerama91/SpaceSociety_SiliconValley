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
import android.net.Uri;
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

public class PdfLibrary extends ListActivity {

    private static final String TITLES[] = {"ARLISS Experimental Rocketry Project with Ken Biba," +
            "May 13, 2015",
            "Mining the Moon with a Lunar Elevator with Charles Radley, May 2, 2015",
            "A Lunar Volatiles Miner",
            " Jacob's Ladder: A Lunar Space Elevator",
            "Lunar Space Elevators for Cislunar Space Development"
    };
    private static final String URL_LIST[] = {"http://www.spacesociety-sv.org/files/104233972.pdf",
            "http://www.spacesociety-sv.org/files/103569018.pdf",
            "http://www.spacesociety-sv.org/files/103752465.pdf",
            "http://www.spacesociety-sv.org/files/103752461.pdf",
            "http://www.spacesociety-sv.org/files/103752463.pdf"
    };
    private static final String FOLDER_LIB = "/SpaceSociety/";
    private static final String FOLDER_NAME = "SpaceSociety";
    private static final String PDF_APP = "application/pdf";
    private static final String ERROR = "No Application Available to View PDF";
    private static final String LOADING_TITLE = "LOADING";
    private static final String LOADING_MSG = "Opening the PDF ..";

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
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile, mContext);
            readPdf(fileUrl, fileName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.dismiss();
        }

    }

    protected void readPdf(String url, String fileName) {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.pdf_library, TITLES));
        mListView = getListView();
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new DownloadFile(PdfLibrary.this).execute(URL_LIST[position], TITLES[position] + ".pdf");
            }
        });
    }

}
