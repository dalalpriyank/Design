package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class cart_selected_details extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog pDialog;
    FloatingActionButton cart;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    String coll_id;
    String depertment;


    EditText dep;
    Button order_now;

    List cart_sel;


    private static String url_all_products = "http://192.168.43.184/design/read_orders.php";
    ListView lists;
    // JSON Node names

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "college_id";
    private static final String TAG_NAME = "food_name";
    private static final String TAG_PRICE = "price";
    private static final String TEG_DESC = "quantity";

    // products JSONArray
    JSONArray products = null;

    private static String url_all_products_tmp = "http://192.168.43.184/design/insert_depertment.php";
    // JSON Node names
    private static final String TAG_SUCCESS_tmp = "success";
    private static final String TAG_PRODUCTS_tmp = "products";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_selected_details);
        lists  = (ListView) findViewById(R.id.cart_details);
        productsList = new ArrayList<HashMap<String,String>>();

        dep  = (EditText) findViewById(R.id.deper);
        order_now = (Button) findViewById(R.id.btn);
        order_now.setOnClickListener(this);

        //this is fetched college id...
        SharedPreferences sharedPreferences=cart_selected_details.this.getSharedPreferences("clgid", Context.MODE_PRIVATE);
        coll_id=sharedPreferences.getString("clg","");

        new cart_selected_details.LoadAllProducts().execute();

    }



    private  class LoadAllProducts extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(cart_selected_details.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("college_id",coll_id));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
            Log.v("All Products: ", json+"");
            try {
                    // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);


                    for (int i = 0; i < products.length(); i++) {

                        JSONObject c = products.getJSONObject(i);
                        // Toast.makeText(AllProductsActivity.this, "here", Toast.LENGTH_LONG).show();

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String price = c.getString(TAG_PRICE);
                        String description = c.getString(TEG_DESC);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_PRICE, price);
                        map.put(TEG_DESC, description);

                        //map.put(TAG_last_name, last_name);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } //Toast.makeText(AllProductsActivity.this, "problems ", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        Log.d("data",productsList+"");

            cart_selected_details.this.runOnUiThread(new Runnable() {
                public void run() {
                    //Updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(
                            cart_selected_details.this, productsList,
                            R.layout.tmp_cart_selected, new String[]{TAG_NAME, TEG_DESC, TAG_PRICE},
                            new int[]{R.id.food_tmp, R.id.qty_tmp,R.id.total_tmp});
                    // updating listview
                    lists.setAdapter(adapter);
                }
            });

        }
    }



    @Override
    public void onClick(View v) {
        depertment = dep.getText().toString();
        //new LoadData().execute(depertment);
        String po=dep.getText().toString();

        if (po.length() == 0) {
            dep.setError("Required...");
        }
        else{
        Toast.makeText(this,"Ordered Placed..", Toast.LENGTH_SHORT).show();
        new cart_selected_details.loadall().execute();
        dep.setText(null);}
    }
    private  class loadall extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("depertment",depertment));
            params.add(new BasicNameValuePair("college_id",coll_id));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products_tmp, "GET", params);
            return null;
        }
    }
}