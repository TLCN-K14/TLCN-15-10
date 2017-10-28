package com.hcmute.trietthao.yourtime.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.hcmute.trietthao.yourtime.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AccountActivity extends AppCompatActivity{

    @Bind(R.id.ln_sign_out)
    LinearLayout mLnSignOut;
    @Bind(R.id.ln_account_details)
    LinearLayout mLnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);

        mLnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details= new Intent(AccountActivity.this, AccountDetailsActivity.class);
                startActivity(details);

            }
        });
        mLnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                alertDialogBuilder.setTitle("Sign Out");
                alertDialogBuilder.setMessage("Are you sure you want to sign out?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
}