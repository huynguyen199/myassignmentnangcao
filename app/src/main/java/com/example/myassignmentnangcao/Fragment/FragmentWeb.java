package com.example.myassignmentnangcao.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myassignmentnangcao.R;

public class FragmentWeb extends Fragment {
    String link;
    WebView wv;

    Button back,forward,reload;


    public FragmentWeb(String link) {
        this.link = link;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web,container,false);
        wv = v.findViewById(R.id.webView1);
        back = v.findViewById(R.id.back);
        forward = v.findViewById(R.id.forward);
        reload = v.findViewById(R.id.reload);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wv.canGoBack()){
                    wv.goBack();
                }else{
                    FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameContainer,new FragmentNews()).commit();
                }
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wv.canGoForward()){
                    wv.goForward();
                }
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.reload();
            }
        });

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv.loadUrl(url);

                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        wv.loadUrl(link);

        v.setFocusableInTouchMode(true);
        v.requestFocus();

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if( i == KeyEvent.KEYCODE_BACK )
                {
                    Log.d("back","back");
                    FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameContainer,new FragmentNews()).commit();
                    return true;
                }
                return true;
            }
        });


        return v;

    }
}
