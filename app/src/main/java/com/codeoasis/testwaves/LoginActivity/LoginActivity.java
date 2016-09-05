package com.codeoasis.testwaves.LoginActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.codeoasis.testwaves.MainActivity;
import com.codeoasis.testwaves.MyService;
import com.codeoasis.testwaves.R;


public class LoginActivity extends AppCompatActivity implements LoginListener {



    private String TAG ="yan ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchTestService();

        setContentView(R.layout.login_main);
        Backendless.initApp(LoginActivity.this, "78E983A5-BBF1-3B4D-FF1B-420A24AD8E00", "3EEA829B-1082-5E4D-FF41-DA466A6CFE00", "v1" );
        showLoginFragment();

    }

    public void launchTestService() {
        Log.d(TAG, "launchTestService: start");
        // Construct our Intent specifying the Service
        Intent i = new Intent(this, MyService.class);
        // Add extras to the bundle
        //i.putExtra("foo", "bar");
        // Start the service
        startService(i);
    }

    private void showLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.contentFragment, new LoginFragment())
                .commit();
    }


    @Override
    public void tryLogin()
    {
        Backendless.UserService.loginWithFacebook( LoginActivity.this, new AsyncCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse( BackendlessUser loggedInUser )
            {
                //Successful login
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                //Unsuccessful login
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } );
    }

    @Override
    public void tryEnterOffline() {
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);

    }


}
