package com.hcmute.trietthao.yourtime.sharedPreferences;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.SignInActivity;

import java.util.HashMap;

public class UserSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "Name";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_AVATAR = "avatar";

    public UserSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserSignInSession(String uName, String uPassword){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_NAME, uName);
        editor.putString(KEY_PASSWORD,  uPassword);
        editor.commit();
    }
    public void createUserSignUpSession(String email, String passw, String name, String image) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, passw);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_AVATAR,image);
        editor.commit();
    }


    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SignInActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_AVATAR, pref.getString(KEY_AVATAR,null));

        // return user
        return user;
    }

    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
