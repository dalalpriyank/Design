package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class vendor_registration extends AppCompatActivity implements View.OnClickListener {
    EditText seller_id,restaurant_name,owner_name,owner_email,password,cpassword,seller_phoneo;
    String sid,res_name,owname,owemail,pass,sellpno,email2;
    Button reg_as_rest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_registration);

        seller_id = (EditText)findViewById(R.id.ed_vendor_id);
        restaurant_name = (EditText)findViewById(R.id.ed_restaurant_name);
        owner_name = (EditText)findViewById(R.id.ed_ower_name);
        owner_email = (EditText)findViewById(R.id.ed_owner_email);
        password =(EditText)findViewById(R.id.ed_owner_password);


        seller_phoneo = (EditText)findViewById(R.id.ed_owner_phoneno);


        reg_as_rest = (Button)findViewById(R.id.btn_vendor_reg);

        reg_as_rest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sid = seller_id.getText().toString();
        res_name = restaurant_name.getText().toString();
        owname = owner_name.getText().toString();
        owemail = owner_email.getText().toString();
        pass = password.getText().toString();
        sellpno = seller_phoneo.getText().toString();

        /*owemail = owemail.getText().toString().trim();*/
        email2 = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";


        if (sid.length() == 0) {
            seller_id.setError("Please enter your ID");
        } else if (sid.length() > 5) {
            seller_id.setError("Must be between 0 to 5!");
        } else if (res_name.length() == 0) {
            restaurant_name.setError("Please Enter Restaurant Name");
        } else if (res_name.length() < 4 || res_name.length() > 40) {
            restaurant_name.setError("Must be between 4 to 40 letters!");
        } else if (owname.length() == 0) {
            owner_name.setError("Required");
        } else if (owname.length() < 4 || owname.length() > 30) {
            owner_name.setError("Must be between 4 to 30 letters!");
        } else if (owemail.length() == 0) {
            owner_email.setError("Please enter Email ID");
        } else if (!owemail.matches(email2)) {
            owner_email.setError("Invalid Email ID");
        } else if (pass.length() == 0) {
            password.setError("Please enter password");
        } else if (pass.length() < 4 || pass.length() > 20) {
            password.setError("Password Invalid");
        } else if (seller_phoneo.length() == 0) {
            seller_phoneo.setError("Please enter your Phone Number");
        } else if (!(seller_phoneo.length() == 10)) {
            seller_phoneo.setError("Enter Valid Phone Number!");
        }

        else {

            new LoadData().execute(sid, res_name, owname, owemail, pass, sellpno);

        }
    }




    public class LoadData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String s=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(vendor_registration.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {


            try {
                /*HttpRequest httpRequest = new HttpRequest("http://192.168.0.100/design/insert_rest.php");*/
                HttpRequest httpRequest = new HttpRequest("http://192.168.43.184/design/insert_rest.php");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("seller_id", strings[0]);
                hashMap.put("restaurant_name", strings[1]);
                hashMap.put("owner_name", strings[2]);
                hashMap.put("owner_email", strings[3]);
                hashMap.put("password", strings[4]);
                hashMap.put("sell_phoneno", strings[5]);

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


                Toast.makeText(getApplicationContext(), "Fill all the fields!" , Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(),"Record Successfully inserted.", Toast.LENGTH_LONG).show();
                Intent reg = new Intent(getApplicationContext(), Vender_login.class);
                startActivity(reg);
            }
        }

    }


}
