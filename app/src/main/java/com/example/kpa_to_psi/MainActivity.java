package com.example.kpa_to_psi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

// Used to inject JavaScript information into the web application
class Interface {
    @JavascriptInterface
    public String toString() {
        return "Hello World";
    }
}
public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new Interface(), "Android");

        WebChromeClient client = new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
                        setTitle("").
                        setMessage(message).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        }).create();
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                //return super.onJsPrompt(view, "", message, defaultValue, result);
                final EditText textbox = new EditText(view.getContext());
                textbox.setText(defaultValue);
                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
                        setTitle("").
                        setMessage(message).
                        setCancelable(true).
                        setView(textbox).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm(String.valueOf(textbox.getText()));
                            }
                        }).
                        setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        }).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                result.cancel();
                            }
                        }).create();
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {


                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
                        setTitle("").
                        setMessage(message).
                        setCancelable(true).
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        }).
                        setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        }).
                        setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                result.cancel();
                            }
                        }).create();
                dialog.show();
                return true;

            }

            public boolean onConsoleMessage(ConsoleMessage cm) {
                Toast.makeText(getApplicationContext(), cm.message(), Toast.LENGTH_LONG).show();
                Log.e("Console", cm.message());
                return true;
            }
        };

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(client);

        webView.loadUrl("file:///android_asset/index.html");
    }
}
