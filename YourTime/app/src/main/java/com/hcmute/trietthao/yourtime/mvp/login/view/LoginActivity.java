package com.hcmute.trietthao.yourtime.mvp.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
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
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.login.adapter.*;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.SignInActivity;
import com.hcmute.trietthao.yourtime.mvp.signUp.view.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    Timer timer;
    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    public static boolean FROM_FB=false;
    public static boolean FROM_GG=false;

    @Bind(R.id.btn_sign_up)
    Button mBtnSignUp;

    @Bind(R.id.btn_login_fb)
    LoginButton mBtnLogin;
    @Bind(R.id.btn_login)
    Button mBtnSignIn;
    @Bind(R.id.txt_login)
    TextView mTxtLoginFB;

    @Bind(R.id.btn_sign_in_google)
    SignInButton mBtnSignInGoogle;
    @Bind(R.id.txt_login_google)
    TextView mTxtLoginGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();


        //Google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mBtnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mBtnSignInGoogle.setScopes(gso.getScopeArray());


        // facebook
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                nextActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        mBtnLogin.setReadPermissions(Arrays.asList("email"));

        mBtnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FROM_FB=true;
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        nextActivity(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
//                Profile profile = Profile.getCurrentProfile();
//                nextActivity(profile);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });


        //slide login
        final int[] mResources = {
                R.drawable.collaborate_login,
                R.drawable.login2

        };
        mCustomPagerAdapter = new CustomPagerAdapter(this, mResources);
        mViewPager = (ViewPager) this.findViewById(R.id.pagerF);
        mViewPager.setAdapter(mCustomPagerAdapter);
        //Xác định thời gian chạy slide ảnh
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mViewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % mResources.length);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Profile profile = Profile.getCurrentProfile();
//        nextActivity(profile);
        hideProgressDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            handleSignInResult(result);
        }


    }

    private void nextActivity(JSONObject object){
        if (FROM_FB) {
            try {
            String id = object.getString("id");
            Intent main = new Intent(LoginActivity.this, SignUpActivity.class);
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");

                Log.e("Vao start activity:", " ");
                main.putExtra("imageUrl", profile_pic.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (object.has("first_name"))
                main.putExtra("name", object.getString("first_name"));
            if (object.has("last_name"))
                main.putExtra("surname", object.getString("last_name"));
            if (object.has("email")){
                main.putExtra("email", object.getString("email"));
            }

            startActivity(main);


        }catch(JSONException e) {
            Log.d(TAG,"Error parsing JSON");
        }
    }

    }
    protected void initListener(){
        mBtnSignUp.setOnClickListener(this);
        mBtnSignIn.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTxtLoginFB.setOnClickListener(this);
        mBtnSignInGoogle.setOnClickListener(this);
        mTxtLoginGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==mTxtLoginFB){
            mBtnLogin.performClick();
        }
        if(view==mTxtLoginGoogle){
            mBtnSignInGoogle.performClick();
        }
        switch (view.getId()){
            case R.id.btn_login:
                Intent signIn= new Intent(this, SignInActivity.class);
                startActivity(signIn);
                break;
            case R.id.btn_sign_up:
                Intent signUp= new Intent(this, SignUpActivity.class);
                startActivity(signUp);
                break;
            case R.id.txt_login_google:
                FROM_GG=true;
                signIn();
                break;
        }

    }
    //Login gg

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {

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
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if(FROM_GG){
                Log.e("Vao from gg intent", "");
                Intent main = new Intent(LoginActivity.this, SignUpActivity.class);
                main.putExtra("name", acct.getDisplayName());
                main.putExtra("email", acct.getEmail());
                main.putExtra("imageUrl", acct.getPhotoUrl());
                startActivity(main);
            }

        }
    }
    private void signIn() {
        Log.e("Vào sign in"," ");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        Log.e("Vào sign out"," ");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }



}