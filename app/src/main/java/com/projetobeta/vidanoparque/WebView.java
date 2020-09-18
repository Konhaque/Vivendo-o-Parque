package com.projetobeta.vidanoparque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebViewClient;

import com.projetobeta.vidanoparque.generalfunctions.Fullscreen;

public class WebView extends AppCompatActivity {
    private android.webkit.WebView webView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fullscreen(this);
        setContentView(R.layout.activity_web_view);
        iniciaObjetos();
    }

    private void iniciaObjetos(){
        webView = (android.webkit.WebView) findViewById(R.id.web_view);
        toolbar = (Toolbar) findViewById(R.id.tb);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://vivendooparque.azurewebsites.net/pituacu/pituacu.html");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}