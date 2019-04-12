package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;




public class Register_Activity extends AppCompatActivity {
    EditText name, clgid, password,cpassword, email, phoneno, institute;
    Button register;

    String s_name, s_clgid, s_password, s_email,email2, s_phoneno, s_institute,clgid2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        name = (EditText) findViewById(R.id.ed_fullname);
        clgid = (EditText) findViewById(R.id.ed_college_id);
        password = (EditText) findViewById(R.id.ed_password);
        email = (EditText) findViewById(R.id.ed_email);
        phoneno = (EditText) findViewById(R.id.ed_phoneno);
        institute = (EditText) findViewById(R.id.ed_institute);
        register = (Button) findViewById(R.id.btn_reg);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_name = name.getText().toString();
                s_clgid = clgid.getText().toString();
                s_password = password.getText().toString();
                //s_c_password = cpassword.getText().toString();
                //   s_email = email.getText().toString();
                s_phoneno = phoneno.getText().toString();
                s_institute = institute.getText().toString();

                //  Toast.makeText(getApplicationContext(), "Successfully fetched...", Toast.LENGTH_SHORT).show();
                //finish();

                s_email = email.getText().toString().trim();
                email2  =   "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
                clgid2  =   "[0-9]+[a-z]+[0-9]+";
                //name2   =   "[a-zA-Z]+ [a-zA-Z]+";

               if (s_name.length() == 0) {
                    name.setError("Please enter valid name");
                    name.requestFocus();
                } else if (s_name.length()<6||s_name.length()>25) {
                    name.setError("Name must be between 6 to 25 letters)");
                    name.requestFocus();
                } else if (!s_clgid.matches(clgid2)) {
                    clgid.setError("Invalid college ID");
                    clgid.requestFocus();
                } else if (s_clgid.length() == 0) {
                    clgid.setError("Please enter college ID!");
                    clgid.requestFocus();
                } else if (s_clgid.length()>11) {
                    clgid.setError("Must be upto 10 letters");
                    clgid.requestFocus();
                } else if (s_password.length() == 0) {
                    password.setError("Please enter Password!");
                    password.requestFocus();
                } else if (s_password.length()<4||s_password.length()>12) {
                    password.setError("Password must be between 4 to 12 letters)");
                    password.requestFocus();
                }  else if (s_email.length() == 0) {
                    email.setError("Please enter Email ID!");
                    email.requestFocus();
                } else if (!s_email.matches(email2)) {
                    email.setError("Email is Invalid");
                    email.requestFocus();
                }  else if (s_phoneno.length() == 0) {
                    phoneno.setError("Please enter Mobile Number!");
                    phoneno.requestFocus();
                } else if (!(s_phoneno.length() == 10)) {
                    phoneno.setError("Check Mobile Number");
                    phoneno.requestFocus();
                }  else if (s_institute.length() == 0) {
                    institute.setError("Please enter Intitute!");
                    institute.requestFocus();
                } else if (s_institute.length()<1||s_institute.length()>8) {
                    institute.setError("Password must be between 4 to 12 letters)");
                    institute.requestFocus();
                }
                else {
                    new LoadData().execute(s_name, s_clgid, s_password, s_email, s_phoneno, s_institute);
                }


            }
        });


    }

    private class LoadData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String s=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register_Activity.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {


            try {
                /*HttpRequest httpRequest = new HttpRequest("http://192.168.0.100/design/insert.php");*/
                HttpRequest httpRequest = new HttpRequest("http://192.168.43.184/design/insert.php");

                /*HttpRequest httpRequest = new HttpRequest("http://file-manager.hostinger.in/6/insert.php");*/
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("custname", strings[0]);
                hashMap.put("clgid", strings[1]);
                hashMap.put("custpass", strings[2]);
                hashMap.put("custemail", strings[3]);
                hashMap.put("custphn", strings[4]);
                hashMap.put("custinstitute", strings[5]);

                s = (httpRequest.prepare(HttpRequest.Method.POST).withData(hashMap).sendAndReadString());
                Log.d("data", s);


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("aa",e.toString());
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();


            if (s.equalsIgnoreCase("inserted\n")) {
                /*Intent reg = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(reg);*/
                finish();

                Toast.makeText(getApplicationContext(), "Record Successfully inserted.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Fill all the fields!", Toast.LENGTH_LONG).show();
            }
        }

    }


}

