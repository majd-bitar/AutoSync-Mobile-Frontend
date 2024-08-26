package com.example.obdii_reader.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obdii_reader.R;

public class SignupActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_email;
    private EditText ed_number;
    private EditText ed_password;
    private Button bt_signup;

    private ImageView iv_facebook;
    private ImageView iv_google;
    private TextView tv_loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ed_name=findViewById(R.id.name_ed);
        ed_email=findViewById(R.id.email_ed);
        ed_number=findViewById(R.id.number_ed);
        ed_password=findViewById(R.id.password_ed);
        bt_signup=findViewById(R.id.signup_bt);

        iv_facebook=findViewById(R.id.facebook_icon);
        iv_google=findViewById(R.id.google_icon);

        tv_loginLink=findViewById(R.id.login_link_text);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private boolean validateInputs() {
        boolean isValid = true;

        if (ed_name.getText().toString().trim().isEmpty()) {
            ed_name.setError("Name is required");
            isValid = false;
        }

        if (ed_email.getText().toString().trim().isEmpty()) {
            ed_email.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(ed_email.getText().toString().trim()).matches()) {
            ed_email.setError("Enter a valid email address");
            isValid = false;
        }

        if (ed_number.getText().toString().trim().isEmpty()) {
            ed_number.setError("Phone number is required");
            isValid = false;
        }

        if (ed_password.getText().toString().trim().isEmpty()) {
            ed_password.setError("Password is required");
            isValid = false;
        }

        return isValid;
    }


}