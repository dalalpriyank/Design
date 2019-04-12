package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText cname, cpass;

    Button register;
    Button signin;

    String s_name;
    String s_pass;


    // int counter = 3;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cname = (EditText) findViewById(R.id.col_id);
        cpass = (EditText) findViewById(R.id.password);

        signin = (Button) findViewById(R.id.btnsubmit);
        register = (Button) findViewById(R.id.btnregister);



        // tx1 = (TextView)findViewById(R.id.counter);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register_Activity.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "sdcsd", Toast.LENGTH_SHORT).show();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_name = cname.getText().toString();
                s_pass = cpass.getText().toString();

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("chintu", s_name);
                editor.commit();


                if (s_name.length() == 0) {
                    cname.setError("required");
                    cpass.requestFocus();
                } else if (s_pass.length() == 0) {
                    cpass.setError("Requied...");
                    cpass.requestFocus();
                } else {
                    new Login().execute(s_name,s_pass);
                }
            }
        });
    }

    private class Login extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Processing...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String s = null;
            String sname = null;
            String custname=null;

            /*String rname=null;*/
            try {
                HttpRequest httpRequest = new HttpRequest("http://192.168.43.184/design/login.php");
                /*HttpRequest httpRequest = new HttpRequest("http://192.168.0.100/design/login.php");*/

                /*HttpRequest httpRequest = new HttpRequest("http://file-manager.hostinger.in/6/login.php");*/
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("custname", strings[0]);
                hashMap.put("custpass", strings[1]);


                s = httpRequest.prepare(HttpRequest.Method.POST).withData(hashMap).sendAndReadString();
                Log.d("Values..", s);


                //saving username...


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("customers");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    sname = jsonObject1.getString("college_id");
                    custname = jsonObject1.getString("cust_name");

//                    Intent datafororder = new Intent(MainActivity.this,select_quantity.class);
//                    datafororder.putExtra("custid",sname);
//                    startActivity(datafororder);




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("data error", e.toString());

            }


            SharedPreferences sharedPreference = getSharedPreferences("clgid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreference.edit();
            editor.putString("clg",s_name);
            editor.commit();

            SharedPreferences sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorr=sharedPreferences.edit();
            editorr.putString("custname",custname);
            editorr.commit();
            return sname;

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();


            if (s_name.equals(s)) {
                /*Intent log = new Intent(getApplicationContext(),.class);
                startActivity(log);*/
                Intent i = new Intent(MainActivity.this, Navigation_Drawer.class);
                //Intent i = new Intent(MainActivity.this, Vender_login.class);

                startActivity(i);


            } else {
                Toast.makeText(getApplicationContext(), "Wrong email or password...", Toast.LENGTH_LONG).show();
                /*tx1.setVisibility(View.VISIBLE);
                tx1.setBackgroundColor(Color.RED);
                counter--;
                tx1.setText(Integer.toString(counter));

                if (counter == 0) {
                    signin.setEnabled(false);
                }*/

            }
        }
    }
}
