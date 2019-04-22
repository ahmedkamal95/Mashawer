package com.fekratoday.mashawer.screens.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.screens.homescreen.HomeActivity;
import com.fekratoday.mashawer.screens.loginscreen.fragments.LoginFragment;
import com.fekratoday.mashawer.screens.loginscreen.fragments.MainLoginFragment;
import com.fekratoday.mashawer.screens.loginscreen.fragments.SignupFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, LoginCommunicator {

    private LoginContract.Presenter presenter;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private LoginFragment loginFragment;
    private SignupFragment signupFragment;
    private long time;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut();


        presenter = new LoginPresenterImpl(this);


        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            MainLoginFragment mainLoginFragment = new MainLoginFragment();
            loginFragment = new LoginFragment();
            signupFragment = new SignupFragment();
            transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frame, mainLoginFragment, "mainLoginFragment");
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*user = presenter.onStart();
        if (user != null) {
            login(user);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void login(FirebaseUser user) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("email", user.getEmail());
        intent.putExtra("username", user.getDisplayName());
        startActivity(intent);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void replaceFragment(String fragmentTag) {
        transaction = fragmentManager.beginTransaction();
        switch (fragmentTag) {
            case "loginFragment":
                transaction.replace(R.id.frame, loginFragment, "loginFragment");
                break;
            case "signupFragment":
                transaction.replace(R.id.frame, signupFragment, "signupFragment");
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onButtonLoginClick(String email, String password) {
        presenter.onButtonLoginClick(email, password);
    }

    @Override
    public void onButtonRegisterClick(String email, String password, String userName, SignupFragment signupFragment) {
        presenter.onButtonRegisterClick(email, password, userName, signupFragment);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onButtonGoogleClick() {
        presenter.onButtonGoogleClick();
    }

    @Override
    public void onButtonFacebookClick(LoginButton btnFacebook) {
        presenter.onButtonFacebookClick(btnFacebook);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    /**
     * Handle Backpressed to close app
     */
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            if (time + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(LoginActivity.this, "Press again to close", Toast.LENGTH_SHORT).show();
            }
            time = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
