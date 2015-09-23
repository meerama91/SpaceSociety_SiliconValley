package com.spacesociety.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.spacesociety.MainActivity;
import com.spacesociety.R;

/**
 * Created by julep on 9/17/15.
 */
public class MainFragment extends Fragment {

    View rootView;
    FragmentManager fragmentManager;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();
        mainActivity = (MainActivity) getActivity();

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setPdfLibraryButton();
        setMp3LibraryButton();
        setWebsiteButton();
        getActivity().setTitle("Home");
        return rootView;
    }

    private void setPdfLibraryButton() {

        LinearLayout pdfLibraryButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_pdf_library_button);
        pdfLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mainActivity.pdfLibraryFragment)
                        .commit();
            }
        });
    }

    private void setMp3LibraryButton() {

        LinearLayout mp3LibraryButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_mp3_library_button);
        mp3LibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mainActivity.mp3LibraryFragment)
                        .commit();
            }
        });
    }

    private void setWebsiteButton() {

        LinearLayout websiteButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_website_button);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mainActivity.websiteFragment)
                        .commit();
            }
        });
    }


}
