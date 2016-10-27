package com.exxonmobil.mobapp.activity;

/**
 * Created by El Nakeeb on 7/18/2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exxonmobil.mobapp.R;
import com.exxonmobil.mobapp.app.AppConfig;
import com.exxonmobil.mobapp.app.AppController;
import com.exxonmobil.mobapp.helper.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity{

    private static final String TAG = Register.class.getSimpleName();

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextMobile;
    private EditText editTextName;
    private EditText editTextCarBrand;
    private EditText editTextCarModel;
    private EditText editTextCarYear;
    private Button buttonSignup;
    private Button buttonCompReg;
    String name;
    String email;
    String mobile;
    String password;
    String carBrand;
    String carModel;
    String carYear;

    private ProgressDialog pDialog;
    Button btnLinkToLogin;
    private SessionManager session;

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //initializing views
        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextMobile = (EditText) findViewById(R.id.mobile);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextCarBrand = (EditText) findViewById(R.id.carBrand);
        editTextCarModel = (EditText) findViewById(R.id.carModel);
        editTextCarYear = (EditText) findViewById(R.id.carYear);

        buttonSignup = (Button) findViewById(R.id.btnRegister);
        buttonCompReg = (Button) findViewById(R.id.btnCompRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn().equals("1")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getApplicationContext(), Landing.class);
            startActivity(intent);
            finish();
        }



        //attaching listener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting email and password from edit texts
                name = editTextName.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                mobile = editTextMobile.getText().toString().trim();
                password  = editTextPassword.getText().toString().trim();


                //checking if email and passwords are empty
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(getApplicationContext(),"Please enter mobile",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }

                editTextName.setVisibility(View.GONE);
                editTextEmail.setVisibility(View.GONE);
                editTextMobile.setVisibility(View.GONE);
                editTextPassword.setVisibility(View.GONE);
                buttonSignup.setVisibility(View.GONE);

                editTextCarBrand.setVisibility(View.VISIBLE);
                editTextCarModel.setVisibility(View.VISIBLE);
                editTextCarYear.setVisibility(View.VISIBLE);
                buttonCompReg.setVisibility(View.VISIBLE);
                //initializing firebase auth object
//                FirebaseMessaging.getInstance().subscribeToTopic("test");
//                FirebaseInstanceId.getInstance().getToken();

            }
        });

        buttonCompReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carBrand = editTextCarBrand.getText().toString().trim();
                carModel = editTextCarModel.getText().toString().trim();
                carYear = editTextCarYear.getText().toString().trim();
                if(TextUtils.isEmpty(carBrand)){
                    Toast.makeText(getApplicationContext(),"Please enter car brand",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(carModel)){
                    Toast.makeText(getApplicationContext(),"Please enter car model",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(carYear)){
                    Toast.makeText(getApplicationContext(),"Please enter car year",Toast.LENGTH_LONG).show();
                    return;
                }
//                        SharedPreferences prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
//                        String regID = prefs.getString("regID", "");
                String regID = FirebaseInstanceId.getInstance().getToken();
                registerUser(name, email, mobile, password, carBrand, carModel, carYear, regID);
            }
        });

        // Link to Register Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void registerUser(final String name, final String email, final String mobile, final String password, final String carBrand, final String carModel, final String carYear, final String regID){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //if the email and password are not empty
        //displaying a progress dialog

        pDialog.setMessage("Registering please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String message = jObj.getString("message");
                        String name = jObj.getString("name");
                        String email = jObj.getString("email");
                        String mobile = jObj.getString("email");
                        String carBrand = jObj.getString("carBrand");
                        String carModel = jObj.getString("carModel");
                        String carYear = jObj.getString("carYear");
                        String regID = jObj.getString("regID");
                        String created_at = jObj.getString("created_at");

                        session.insertData(name, email, mobile, password, carBrand, carModel, carYear, regID, created_at);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Register.this, Landing.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("carBrand", carBrand);
                params.put("carModel", carModel);
                params.put("carYear", carYear);
                params.put("regID", regID);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}