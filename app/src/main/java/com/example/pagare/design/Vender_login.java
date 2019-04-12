package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class Vender_login extends AppCompatActivity {

    EditText cname, cpass;

    Button register;
    Button signin;

    String s_name;
    String s_pass;
    String sellername;
    String shopname;
    String sellerid;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_login);

        cname = (EditText) findViewById(R.id.u_id);
        cpass = (EditText) findViewById(R.id.u_password);

        signin = (Button) findViewById(R.id.u_btnsignin);
        register = (Button) findViewById(R.id.u_btnregister);





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Vender_login.this, vendor_registration.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "sdcsd", Toast.LENGTH_SHORT).show();

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*SharedPreferences sharedPreferences=getSharedPreferences("userid",Context.MODE_PRIVATE);

                String id=sharedPreferences.getString("vendorid","");
                Toast.makeText(Vender_login.this,id,Toast.LENGTH_SHORT).show();*/

                /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("chintu", s_name);
                editor.commit();*/


                s_name = cname.getText().toString();
                s_pass = cpass.getText().toString();
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
            progressDialog = new ProgressDialog(Vender_login.this);
            progressDialog.setMessage("Processing...");
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {
            String s = null;
            String sname = null;
            /*String rname=null;*/
            try {
                /*HttpRequest httpRequest = new HttpRequest("http://192.168.0.100/design/login_seller.php");*/
                HttpRequest httpRequest = new HttpRequest("http://192.168.43.184/design/login_seller.php");

                /*HttpRequest httpRequest = new HttpRequest("http://file-manager.hostinger.in/6/login.php");*/
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("vendor_id", strings[0]);
                hashMap.put("password", strings[1]);


                s = httpRequest.prepare(HttpRequest.Method.POST).withData(hashMap).sendAndReadString();
                Log.d("Values..", s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("customers");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    sname = jsonObject1.getString("seller_id");
                    sellername = jsonObject1.getString("owner_name");
                    shopname = jsonObject1.getString("restaurant_name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("data error", e.toString());

            }
            SharedPreferences sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("custname",sellername);
            editor.commit();


            SharedPreferences sharedPreferencess = getSharedPreferences("userid1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorr=sharedPreferencess.edit();
            editorr.putString("shopname",shopname);
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
                SharedPreferences sharedPreferences = getSharedPreferences("userid", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("vendorid", cname.getText().toString());
                editor.commit();

                Intent i = new Intent(Vender_login.this, Vender_navigation.class);
                startActivity(i);


                Toast.makeText(getApplicationContext(), "Successfully login...", Toast.LENGTH_LONG).show();



//                Intent sell_data_id = new Intent(c,add_item_fragment.class);
//                sell_data_id.putExtra("daal",s_name);
//                startActivity(sell_data_id);

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
    public void saviid(View view){



    }
}