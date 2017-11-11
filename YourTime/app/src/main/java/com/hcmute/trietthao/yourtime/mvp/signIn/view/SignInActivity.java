package com.hcmute.trietthao.yourtime.mvp.signIn.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.MainActivity;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.signIn.presenter.SignInPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SignInActivity extends AppCompatActivity implements ISignInView {


    @Bind(R.id.btn_sign_in_s)
    Button mBtnSignIn;
    @Bind(R.id.edit_sign_in_email)
    EditText mEditEmail;
    @Bind(R.id.edit_sign_in_pass)
    EditText mEditPassw;

    PreferManager preferManager;
    SignInPresenter signInPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        preferManager = new PreferManager(getApplicationContext());


        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mEditEmail.getText().toString();
                String password = mEditPassw.getText().toString();
                if(username.length()>0&& password.length()>0)
                {
                    signInPresenter.login(username,password);
                    if(username.equals("ltthao@gmail.com") && password.equals("1234")){

                        preferManager.createUserSignInSession("ltthao@gmail.com",
                                "1234");
                        loginSuccess();

                    }else{

                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(getApplication(),"You must enter your email and password before sign in!",Toast.LENGTH_LONG).show();

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

    @Override
    public void loginSuccess() {
        Intent main= new Intent(getApplicationContext(), MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(main);
        finish();
    }

    @Override
    public void errorEmailInvalid() {

    }

    @Override
    public void errorEmptyInput() {

    }
}
