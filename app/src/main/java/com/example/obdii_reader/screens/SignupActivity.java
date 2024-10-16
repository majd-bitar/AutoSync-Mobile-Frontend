package com.example.obdii_reader.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.obdii_reader.R;
import com.majd.obd.reader.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_email;
    private EditText ed_number;
    private EditText ed_password;
    private Button bt_signup;

    private ImageView iv_facebook;
    private ImageView iv_google;
    private TextView tv_loginLink;
    private final String TAG="SignupActivity";

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
                if(validateInputs()){
                    RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                    String url = "http://192.168.1.12:8081/authenticate/signup";

                    JSONObject jsonBody = new JSONObject();
                    try {
                        //jsonBody.put("email", ed_email.getText().toString());
                        jsonBody.put("password", ed_password.getText().toString());
                        //jsonBody.put("phoneNumber",ed_number.getText().toString());
                        L.i(ed_email.getText().toString().substring(0, ed_email.getText().toString().indexOf("@")));
                        jsonBody.put("username",ed_email.getText().toString().substring(0, ed_email.getText().toString().indexOf("@")));
                        String []names = ed_name.getText().toString().split(" ");
                        //jsonBody.put("firstName",names[0]);
                        //jsonBody.put("middleName",names[1]);
                        //jsonBody.put("lastName",names[2]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(SignupActivity.this, "Response is: " + response, Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("SignupActivity", "Error: " + error.getMessage(), error);
                        }
                    }) {
                        @Override
                        public byte[] getBody() {
                            return jsonBody.toString().getBytes(); // Convert JSON object to byte array
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8"; // Set the content type to JSON
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json; charset=utf-8"); // Set Content-Type header
                            // Add other headers if needed
                            return headers;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000, // Timeout in milliseconds (e.g., 10 seconds)
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // Backoff multiplier

                    queue.add(stringRequest);

                    /*
                    RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                    String url = "http://10.0.2.2:8081/authenticate/signup";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(SignupActivity.this, "Response is: " + response, Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("SignupActivity", "Error: " + error.getMessage(), error);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", ed_email.getText().toString());
                            params.put("password", ed_password.getText().toString());
                            params.put("phoneNumber",ed_number.getText().toString());
                            String []names = ed_name.getText().toString().split(" ");
                            params.put("firstName",names[0]);
                            params.put("middleName",names[1]);
                            params.put("lastName",names[2]);
                            return params; // Return the parameters to be sent
                        }
                    };

                     // Add the request to the RequestQueue.
                    queue.add(stringRequest);*/


                }
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
        String regex = "\\+961\\d{8}";
        if(!ed_number.getText().toString().trim().matches(regex)){
            ed_number.setError("Incorrect phone number format");
            isValid = false;
        }

        return isValid;
    }


}