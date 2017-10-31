package com.hcmute.trietthao.yourtime;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hcmute.trietthao.yourtime.profile.AccountDetailsActivity;


public class AccountFragment extends Fragment {

    LinearLayout mLnSignOut;
    LinearLayout mLnDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_account, container, false);

        mLnSignOut=(LinearLayout) V.findViewById(R.id.ln_sign_out);
        mLnDetails=(LinearLayout) V.findViewById(R.id.ln_account_details);

        mLnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details= new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(details);

            }
        });
        mLnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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

        return V;
    }
}
