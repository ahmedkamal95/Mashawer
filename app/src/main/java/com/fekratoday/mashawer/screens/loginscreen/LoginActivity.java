package com.fekratoday.mashawer.screens.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
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
        transaction = fragmentManager.beginTransaction();
        MainLoginFragment mainLoginFragment = new MainLoginFragment();
        LoginFragment loginFragment = new LoginFragment();
        SignupFragment signupFragment = new SignupFragment();
        transaction.add(R.id.frame, mainLoginFragment, "mainLoginFragment");
//        transaction.add(R.id.frame, loginFragment, "loginFragment");
//        transaction.add(R.id.frame, signupFragment, "signupFragment");
        transaction.commit();
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
        Toast.makeText(LoginActivity.this, user.getEmail() + "Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("email", user.getEmail());
        startActivity(intent);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
