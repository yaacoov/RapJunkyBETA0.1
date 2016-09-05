package com.codeoasis.testwaves.LoginActivity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codeoasis.testwaves.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private LoginListener loginListener;
    private Button loginButton;
    private Button skipLoginButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            loginListener = (LoginListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginListener = null;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(v);


        return v;
    }

    private void initViews(View v) {
        loginButton = (Button)v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.tryLogin();
            }
        });

        skipLoginButton = (Button)v.findViewById(R.id.skip_login_button);
        skipLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.tryEnterOffline();
            }
        });
    }


}
