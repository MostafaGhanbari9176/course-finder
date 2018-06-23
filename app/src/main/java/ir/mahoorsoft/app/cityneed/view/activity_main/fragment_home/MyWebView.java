package ir.mahoorsoft.app.cityneed.view.activity_main.fragment_home;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by M-gh on 07-Jun-18.
 */

public class MyWebView extends WebViewClient {

    @Deprecated
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        view.setVisibility(View.VISIBLE);
        return true;
    }



}

