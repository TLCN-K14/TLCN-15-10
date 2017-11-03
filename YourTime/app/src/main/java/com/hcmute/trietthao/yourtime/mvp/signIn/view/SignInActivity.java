package com.hcmute.trietthao.yourtime.mvp.signIn.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hcmute.trietthao.yourtime.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SignInActivity extends AppCompatActivity {

    @Bind(R.id.btn_sign_in_s)
    Button mBtnSignIn;
    @Bind(R.id.edit_sign_in_email)
    EditText mEditEmail;
    @Bind(R.id.edit_sign_in_pass)
    EditText mEditPassw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
        Log.e("sign in activity","");

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditEmail.getText().toString().matches("")||!isEmailValid(mEditEmail.getText().toString())){
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignInActivity.this);
                    alertDialogBuilder.setTitle("Message");
                    alertDialogBuilder.setMessage("You must enter your email before sign in!");
                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else if(mEditPassw.getText().toString().matches("")){
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignInActivity.this);
                    alertDialogBuilder.setTitle("Message");
                    alertDialogBuilder.setMessage("You must enter password before sign in!");
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

            }
        });


    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
