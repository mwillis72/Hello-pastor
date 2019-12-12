package com.amal.hellochurch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class TDJAKES extends AppCompatActivity {
    private WebView myWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WebView myWebview = (WebView) findViewById(R.id.tdjakesview);
//        myWebview.loadUrl("https://thepottershouse.org/");


//        WebView mWebView = new WebView(this);
//        setContentView(mWebView);
//
//        mWebView.loadUrl("https://thepottershouse.org/");
                setContentView(R.layout.tdjakes);
                 myWebview = (WebView) findViewById(R.id.tdjakesview);
        WebSettings webSettings = myWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebview.loadUrl("https://thepottershouse.org/");
        myWebview.setWebViewClient(new MyWebViewClient());


    }

    private class MyWebViewClient extends WebViewClient {
        @Override public boolean shouldOverrideUrlLoading(WebView view, String url){
            if(Uri.parse(url).getHost().equals("www.thepottershouse.org")){
                return false;
            } else{
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(intent);
                    return true;
            }

        }
    }
}