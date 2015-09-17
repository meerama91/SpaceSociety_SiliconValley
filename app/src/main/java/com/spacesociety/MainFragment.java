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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        LinearLayout pdfLibraryButton = (LinearLayout)rootView.findViewById(R.id.linearLayout_pdf_library_button);
        //TextView pdfLibraryButton = (TextView)getView().findViewById(R.id.textView_pdf_library_button);
        pdfLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PdfLibraryFragment();
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });

        return rootView;
    }



}
