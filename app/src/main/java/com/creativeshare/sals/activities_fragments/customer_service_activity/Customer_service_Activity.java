package com.creativeshare.sals.activities_fragments.customer_service_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.creativeshare.sals.R;
import com.creativeshare.sals.tags.Tags;

public class Customer_service_Activity extends AppCompatActivity {
private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_service_);
        mWebView =  findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if(getIntent().getStringExtra("link")==null){
            mWebView.loadUrl(Tags.base_url);

        }
        else {
            mWebView.loadUrl(getIntent().getStringExtra("link"));

        }
    }
}
