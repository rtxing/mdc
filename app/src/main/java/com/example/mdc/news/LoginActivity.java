package com.example.mdc.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
        private static final String TAG = LoginActivity.class.getSimpleName();
        private static final int RC_SIGN_IN = 007;

        private GoogleApiClient mGoogleApiClient;
        private ProgressDialog mProgressDialog;
        int regVar;
        //private SignInButton btnSignIn;
        private Button btnSignOut, btnRevokeAccess;
        private LinearLayout llProfileLayout;
        private ImageView imgProfilePic;
        private TextView txtName, txtEmail;
        @Override
        public void onClick(View v) {
           /*     int id = v.getId();

                switch (id) {
                        case R.id.btn_sign_in:
                                signIn();
                                break;

                        case R.id.btn_sign_out:
                                signOut();
                                break;

                        case R.id.btn_revoke_access:
                                revokeAccess();
                                break;
                }*/
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d(TAG, "onConnectionFailed:" + connectionResult);

        }

        private EditText inputEmail, inputPassword;
        private FirebaseAuth auth;
        private ProgressBar progressBar;
        private Button btnSignup, btnLogin, btnReset;
        ImageView btnSignIn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.activity_login);
                try {
                        if (getCallingActivity() != null) {
                                regVar = 1;
                        }
                        else {
                                regVar = 0;
                        }
                        btnSignIn = (ImageView) findViewById(R.id.b_gmail);
                        //    btnSignOut = (Button) findViewById(R.id.btn_sign_out);
                        //  btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
                        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
                        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
                        txtName = (TextView) findViewById(R.id.txtName);
                        txtEmail = (TextView) findViewById(R.id.txtEmail);

                        //  btnSignIn.setOnClickListener(this);
                        // btnSignOut.setOnClickListener(this);
                        //btnRevokeAccess.setOnClickListener(this);

                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build();

                        //  mGoogleApiClient = new GoogleApiClient.Builder(this)
                        //        .enableAutoManage(this, this)
                        //      .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

                        // Customizing G+ button
//        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
//        btnSignIn.setScopes(gso.getScopeArray());

                        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
                        if ((auth.getCurrentUser() != null) && (regVar == 0)) {
            startActivity(new Intent(LoginActivity.this, Newsmain.class));
            finish();
        }

                        // set the view now

                        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                        //setSupportActionBar(toolbar);

                        inputEmail = (EditText) findViewById(R.id.email);
                        inputPassword = (EditText) findViewById(R.id.password);
                        progressBar = (ProgressBar) findViewById(R.id.progressBar);
                        btnSignup = (Button) findViewById(R.id.btn_signup);
                        btnLogin = (Button) findViewById(R.id.btn_login);
                        btnReset = (Button) findViewById(R.id.btn_reset_password);


                        //Get Firebase auth instance
      /*  btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Newsmain.class));
            }
        });*/
                        auth = FirebaseAuth.getInstance();

                        btnSignup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                                }
                        });

                        btnReset.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                                }
                        });

                        btnLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        String email = inputEmail.getText().toString();
                                        final String password = inputPassword.getText().toString();

                                        if (TextUtils.isEmpty(email)) {
                                                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                                                return;
                                        }

                                        if (TextUtils.isEmpty(password)) {
                                                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                                                return;
                                        }

                                        progressBar.setVisibility(View.VISIBLE);

                                        //authenticate user
                                        auth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                // If sign in fails, display a message to the user. If sign in succeeds
                                                                // the auth state listener will be notified and logic to handle the
                                                                // signed in user can be handled in the listener.
                                                                progressBar.setVisibility(View.GONE);
                                                                if (!task.isSuccessful()) {
                                                                        // there was an error
                                                                        if (password.length() < 6) {
                                                                                inputPassword.setError(getString(R.string.minimum_password));
                                                                        } else {
                                                                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                                                        }
                                                                } else {
                                                                        Intent intent = new Intent(LoginActivity.this, Newsmain.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                }
                                                        }
                                                });
                                }
                        });
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void signIn() {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
        }


        private void signOut() {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                        updateUI(false);
                                }
                        });
        }

        private void revokeAccess() {
                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                        updateUI(false);
                                }
                        });
        }

        private void handleSignInResult(GoogleSignInResult result) {
                Log.d(TAG, "handleSignInResult:" + result.isSuccess());
                if (result.isSuccess()) {
                        // Signed in successfully, show authenticated UI.
                        GoogleSignInAccount acct = result.getSignInAccount();

                        Log.e(TAG, "display name: " + acct.getDisplayName());

                        String personName = acct.getDisplayName();
                        String personPhotoUrl = acct.getPhotoUrl().toString();
                        String email = acct.getEmail();

                        Log.e(TAG, "Name: " + personName + ", email: " + email
                                + ", Image: " + personPhotoUrl);

                        txtName.setText(personName);
                        txtEmail.setText(email);
                        Glide.with(getApplicationContext()).load(personPhotoUrl)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imgProfilePic);

                        updateUI(true);
                } else {
                        // Signed out, show unauthenticated UI.
                        updateUI(false);
                }
        }

        private void updateUI(boolean isSignedIn) {
                if (isSignedIn) {
                        btnSignIn.setVisibility(View.GONE);
                        btnSignOut.setVisibility(View.VISIBLE);
                        btnRevokeAccess.setVisibility(View.VISIBLE);
                        llProfileLayout.setVisibility(View.VISIBLE);
                } else {
                        btnSignIn.setVisibility(View.VISIBLE);
                        btnSignOut.setVisibility(View.GONE);
                        btnRevokeAccess.setVisibility(View.GONE);
                        llProfileLayout.setVisibility(View.GONE);
                }
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                        handleSignInResult(result);
                }
        }

 /*   @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }



    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
*/
}