package com.test.vkclient.view.fragment.login;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.vkclient.R;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.model.VKScopes;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Вью для логина
 */
public class LoginFragment extends Fragment {

    @Bind(R.id.loginButton)Button loginButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_login, container, false);
        ButterKnife.bind(this, view);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(getActivity(), VKScopes.WALL);
            }
        });
        return view;
    }

}
