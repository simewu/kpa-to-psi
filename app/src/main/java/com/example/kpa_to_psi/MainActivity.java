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

        // Load the webpage into the WebView
        webView.loadUrl("file:///android_asset/index.html");

        /*
<!DOCTYLE html>
<html>
	<head>
		<meta charset='utf-8'/>
		<meta name='viewport' content='user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1'>
		<link rel='shortcut icon' type='image/png' href='favicon.png'/>
		<title>KPA to PSI</title>
		<style>
			html {
				height: 100%;
				margin: 0;
				padding: 0px;
				width: 100%;
				font-family: Tahoma, Geneva, sans-serif;
			}
			body {
				background-color: #32383e;
				overscroll-behavior-x: none;
				overscroll-behavior-y: none;
				height: 100%;
				width: 100%;
			}
			body, tr, td {
				margin: 0;
				padding: 0;
			}
			input {
				font-size: 1em;
				width: 100%;
				padding: 5px;
				border: none;
				border-radius: 3px;
				background-color: #E9ECEF;
				text-align: center;
			}
			input::selection {
				color: #FFF;
				background: #32383e;
			}
			button {
				border: none;
				background-color: #E9ECEF;
			}
			#swap {
				font-size: 1em;
				width: 50%;
				min-width: 73px;
				padding: 5px;
				border-radius: 1000px;
				margin: 10px 0;
				border: none;
			}
			#swap:focus {
				outline: none;
				box-shadow: none;
			}
			#swap:hover {
				background-color: #FFE694;
			}
			#header {
				font-size: 1em;
				background-color: #FFE694;
				color: #000;
				padding: 10px;
				height: 30px;
			}
			#layout {
				width: 100%;
				height: 100%;
				padding: 0;
				margin: 0;
				text-align: center;
				font-size: 1.5em;
			}
			#controls {
				width: 100%;
				height: 100%;
				font-size: 1em;
				text-align: center;
				padding: 5px 30px 30px 30px;
			}
			.label, #label1, #label2 {
				color: #FFE694;
				padding-right: 10px;
			}
			#tinyText1, #tinyText2 {
				float: right;
				font-size: 0.8em;
				color: #FFE694;
			}
			#tinyText {
				float: right;
				font-size: 0.5em;
				color: #FFE694;
			}
			#ratio {
				font-size: 1.5em;
			}
		</style>
	</head>
	<body>
		<table id='layout'>
			<tr>
				<td id='header'><div >Ethan's <b>kPa &#11020 PSI</b> Conversions</div></td>
			</tr>
			<tr>
				<td>


					<table id='controls'>
						<tr>
							<td colspan='1'><span id='label1'>kPa</span></td>
							<td colspan='2'>
								<input id='label1value' type='number' onfocus='this.select();' oninput='updateConversion(this)'/>
								<a id='tinyText1' onclick='loadToMeasured(this)'>Load into measured</a>
							</td>
						</tr>
						<tr>
							<td colspan='1'></td>
							<td colspan='2'><button id='swap' onclick='swapConversion()'>Swap</button></td>
						</tr>
						<tr>
							<td colspan='1'><span id='label2'>PSI</span></td>
							<td colspan='2'>
								<input id='label2value' type='number' onfocus='this.select();' oninput='updateConversion(this)'/>
								<a id='tinyText2' onclick='loadToMeasured(this)'>Load into measured</a>
							</td>
						</tr>
						<tr><td colspan='3'><hr></td></tr>
						<tr>
							<td colspan='2' class='label'>Measured</td>
							<td colspan='1'><input id='measuredValue' type='number' onfocus='this.select();' oninput='updateRatio()'/></td>
						</tr>
						<tr>
							<td colspan='2' class='label'>Target</td>
							<td colspan='1'><input id='targetValue' type='number' onfocus='this.select();' oninput='updateRatio()'/></td>
						</tr>
						<tr>
							<td colspan='3' id='ratio' class='label' style='text-align:right;color:#FFF'>
								Ratio: <span id='ratioValue'></span>
								<br><a id='tinyText' onclick='clearData(this)'>Clear</a>
							</td>
						</tr>
					</table>


				</td>
			</tr>
		</table>

	</body>
	<script type='text/javascript'>
		'use strict';

		var swapped = false;

		function clearData() {
			var c = confirm('Are you sure you would like to remove all the data?');
			if(!c) return;
			if(swapped) swapConversion();
			localStorage.clear();
			load();
		}

		function swapConversion() {
			if(swapped) {
				swapped = false;
				document.getElementById('label1').innerText = 'kPa';
				document.getElementById('label2').innerText = 'PSI';
				var temp = document.getElementById('label1value').value;
				document.getElementById('label1value').value = document.getElementById('label2value').value;
				document.getElementById('label2value').value = temp;
				updateConversion(document.getElementById('label2value'));
			} else {
				swapped = true;
				document.getElementById('label1').innerText = 'PSI';
				document.getElementById('label2').innerText = 'kPa';
				var temp = document.getElementById('label1value').value;
				document.getElementById('label1value').value = document.getElementById('label2value').value;
				document.getElementById('label2value').value = temp;
				updateConversion(document.getElementById('label1value'));
			}
			//console.log('The values have been swapped');
		}

		function updateConversion(element) {
			var value = element.value;

			if(swapped) {
				if(element.id == 'label1value') {
					document.getElementById('label2value').value = (value / 0.14503773800722);
				} else {
					document.getElementById('label1value').value = (value * 0.14503773800722);
				}
			} else {
				if(element.id == 'label1value') {
					document.getElementById('label2value').value = (value * 0.14503773800722);
				} else {
					document.getElementById('label1value').value = (value / 0.14503773800722);
				}
			}
			save();
		}

		function loadToMeasured(element) {
			if(element.id == 'tinyText1') {
				document.getElementById('measuredValue').value = (parseFloat(document.getElementById('label1value').value) || 0).toString();
			} else {
				document.getElementById('measuredValue').value = (parseFloat(document.getElementById('label2value').value) || 0).toString();
			}
			updateRatio();
		}

		function updateRatio() {
			var value1 = 0, value2 = 0;

			value1 = parseFloat(document.getElementById('measuredValue').value) || 0;
			value2 = parseFloat(document.getElementById('targetValue').value) || 0;
			if(value2 == 0) {
				document.getElementById('ratioValue').innerText = '0';
				return;
			}
			document.getElementById('ratioValue').innerText = (value1 / value2).toFixed(6);
			save();
		}

		function save() {
			var val1 = document.getElementById('label1value').value;
			var val2 = document.getElementById('label2value').value;
			var val3 = document.getElementById('measuredValue').value;
			var val4 = document.getElementById('targetValue').value;

			localStorage.setItem('swapped', swapped);
			localStorage.setItem('val1', val1);
			localStorage.setItem('val2', val2);
			localStorage.setItem('val3', val3);
			localStorage.setItem('val4', val4);
		}

		function load() {
			var _swapped = (localStorage.getItem('swapped') == 'true');
			var val1 = localStorage.getItem('val1');
			var val2 = localStorage.getItem('val2');
			var val3 = localStorage.getItem('val3');
			var val4 = localStorage.getItem('val4');

			if(swapped != _swapped) swapConversion();

			document.getElementById('label1value').value = val1 || '';
			document.getElementById('label2value').value = val2 || '';
			document.getElementById('measuredValue').value = val3 || 0;
			document.getElementById('targetValue').value = val4 || 14.7;

			updateRatio();
		}

		load();
	</script>
</html>
*/
    }
}
