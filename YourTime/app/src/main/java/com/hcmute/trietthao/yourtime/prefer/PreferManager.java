package com.hcmute.trietthao.yourtime.prefer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.SignInActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class PreferManager implements DBNguoiDungServer.userListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    DBNguoiDungServer dbNguoiDungServer;
    NguoiDungModel nguoiDungModel;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";
    public static final String KEY_NAME = "Name";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ID="ID";

    public PreferManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserSignInSession(int id, String name, String email){
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        dbNguoiDungServer = new DBNguoiDungServer(this);
        dbNguoiDungServer.getNguoiDung(email);
        editor.commit();
    }

    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

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
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    public Boolean isLoggedIn(){
        return !KEY_EMAIL.equals("Email");
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
    public NguoiDungModel getUserPrefer(){
        return nguoiDungModel;

    }


    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {
        nguoiDungModel=user;
    }
    public int getID(){
        return Integer.parseInt(KEY_ID);
    }
}
