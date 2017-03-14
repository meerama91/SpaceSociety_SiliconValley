package com.spacesociety.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.spacesociety.R;

/**
 * Created by julep on 9/18/15.
 */
public class WebsiteFragment extends Fragment {

    private static final String WEBSITE_URL = "http://www.spacesociety-sv.org/";
    private static final String NET_ERROR = "DEVICE IS NOT CONNECTED TO THE INTERNET";

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Space Society SV Website");

        View rootView = inflater.inflate(R.layout.fragment_website, container, false);

        if ((netCheck())) {
            Toast.makeText(getActivity().getApplicationContext(), NET_ERROR,
                    Toast.LENGTH_SHORT).show();
        } else {
            mWebView = (WebView)rootView.findViewById(R.id.webView_website_fragment);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.loadUrl(WEBSITE_URL);
        }

        return rootView;
    }

    public boolean netCheck() {

        boolean status = true;
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
}
