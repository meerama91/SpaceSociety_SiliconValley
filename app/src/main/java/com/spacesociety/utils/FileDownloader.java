/*
* created by Meera Mali, 2015.
*
*/
package com.spacesociety.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {
    private static final int MEGABYTE = 1024 * 1024;

    private static URL mUrl;
    private static HttpURLConnection mUrlConnection = null;

    public static void downloadFile(String fileUrl, File directory, Context context) {
        try {

            mUrl = new URL(fileUrl);
            mUrlConnection = (HttpURLConnection) mUrl.openConnection();

            mUrlConnection.connect();
            if (mUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Toast.makeText(context, "Server returned HTTP: " + mUrlConnection.getResponseCode()
                                + " " + mUrlConnection.getResponseMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            InputStream inputStream = mUrlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {


                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
