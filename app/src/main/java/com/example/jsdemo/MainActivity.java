package com.example.jsdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView contentWebView;
    private final int REQUEST_CODE_CALLPHONEPERNISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentWebView = (WebView) findViewById(R.id.webview);
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.loadUrl("file:///android_asset/web.html");
        contentWebView.addJavascriptInterface(MainActivity.this, "android");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentWebView.loadUrl("javascript:javacalljs()");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
            }
        });


    }

    @JavascriptInterface
    public void startFunction() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_LONG).show();
            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
            }
        });
    }

    @JavascriptInterface
    public void callphone(String num) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+num));
        startActivity(intent);
    }

    @JavascriptInterface
    public void sendmessage(String num,String txt){
        if(PhoneNumberUtils.isGlobalPhoneNumber(num)){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:"+num));
            intent.putExtra("sms_body", txt);
            startActivity(intent);

        }
    }

    /*public boolean hascallpermission(){
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE);
    }

    public void applicallphonepermission(){
        String[] arr = {Manifest.permission.CALL_PHONE};
        ActivityCompat.requestPermissions(this,arr,REQUEST_CODE_CALLPHONEPERNISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CALLPHONEPERNISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText(MainActivity.this, "动态权限申请成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "动态权限申请不成功", Toast.LENGTH_LONG).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}
