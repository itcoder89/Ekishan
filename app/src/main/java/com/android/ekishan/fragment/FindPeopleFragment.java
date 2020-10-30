package com.android.ekishan.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;



public class FindPeopleFragment extends Fragment {

    public FindPeopleFragment() {
    }


    private ImageView imgBtnProfileBack;
    private TextView txtdescription, txtTitle;
    RelativeLayout relativeLayout;
    ProgressBar progress_circular;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_people, container, false);
        txtdescription = (TextView) view.findViewById(R.id.description);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        myWebView = (WebView) view.findViewById(R.id.webview);
        progress_circular =  view.findViewById(R.id.progress_circular);

        imgBtnProfileBack = (ImageView) view.findViewById(R.id.imgBtnProfileBack);
        String url= ApiClient.BASE_URL+"packages/mobile/"+ MyApplication.getCustomerId();
        loadurl(url);
        return view;
    }


    WebView myWebView;
    public void loadurl(String url) {
        progress_circular.setVisibility(View.VISIBLE);
        Open_Api(url);
    }
    public void Open_Api(String file_path){


        if (myWebView==null){}else {
//            http://drive.google.com/viewerng/viewer?embedded=true&url=" +
            myWebView.loadUrl( file_path);
            myWebView.setWebViewClient(new MyBrowser());
            myWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        progress_circular.setVisibility(View.GONE);

                    } else {
                        progress_circular.setVisibility(View.VISIBLE);

                    };

                }
            });
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
                        myWebView.goBack();
                        return true;
                    }

                    return false;
                }
            });
        }
    }
    public class MyBrowser extends WebViewClient {
        ProgressDialog pd;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress_circular.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress_circular.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }

}
