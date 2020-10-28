package com.example.myassignmentnangcao.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myassignmentnangcao.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FragmentSocial extends Fragment {

    CallbackManager callbackManager;
    LoginButton loginButton;
    ImageView imageView;
    TextView idfb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social,container,false);
        loginButton = v.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();

        imageView = v.findViewById(R.id.imageView);
        idfb = v.findViewById(R.id.idfb);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                idfb.setText("user id: "+loginResult.getAccessToken().getUserId());
                Glide.with(container.getContext())
                        .load("https://graph.facebook.com/" +loginResult.getAccessToken().getUserId() + "/picture?type=normal")
                        .into(imageView);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
