/*
* created by Meera Mali, 2015.
*
*/

package com.spacesociety;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends Activity {

    private static final String WEBSITE_URL = "http://www.spacesociety-sv.org/";
    private static final String NET_ERROR = "DEVICE IS NOT CONNECTED TO THE INTERNET";


    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.website_view);
        if ((netCheck())) {
            Toast.makeText(WebActivity.this, NET_ERROR,
                    Toast.LENGTH_SHORT).show();
        } else {
            mWebView = (WebView) findViewById(R.id.webView);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.loadUrl(WEBSITE_URL);
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

}
