package com.spacesociety;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by julep on 9/17/15.
 */
public class MainFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setPdfLibraryButton();
        setMp3LibraryButton();
        setWebsiteButton();
        return rootView;
    }

    private void setPdfLibraryButton() {

        LinearLayout pdfLibraryButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_pdf_library_button);
        pdfLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PdfLibraryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "PdfLibraryFragment")
                        .commit();
            }
        });
    }

    private void setMp3LibraryButton() {

        LinearLayout mp3LibraryButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_mp3_library_button);
        mp3LibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Mp3LibraryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "Mp3LibraryFragment")
                        .commit();
            }
        });
    }

    private void setWebsiteButton() {

        LinearLayout websiteButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_website_button);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new WebsiteFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "WebsiteFragment")
                        .commit();
            }
        });
    }


}
