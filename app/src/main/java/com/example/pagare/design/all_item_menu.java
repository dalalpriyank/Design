package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class all_item_menu extends AppCompatActivity {

    private ProgressDialog pDialog;
    FloatingActionButton cart;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;

    String sell_id="";



    // url to get all products list
    /*private static String url_all_products = "http://192.168.0.100/design/read_manu.php";*/
    private static String url_all_products = "http://192.168.43.184/design/read_manu.php";
    ListView lists;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "seller_id";
    private static final String TAG_NAME = "food_name";
    private static final String TAG_PRICE = "price";
    private static final String TEG_DESC = "description";

    // products JSONArray
    JSONArray products = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_item_menu);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent ints= getIntent();
        sell_id = ints.getStringExtra("sid");

        lists  = (ListView) findViewById(R.id.list_menu);
        productsList = new ArrayList<HashMap<String,String>>();

        cart = (FloatingActionButton) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(all_item_menu.this,"This is Cart Button",Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(all_item_menu.this,cart_selected_details.class);
                startActivity(intent);

            }
        });
        new LoadAllProducts().execute();
    }

    private class LoadAllProducts  extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(all_item_menu.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("seller_idsi",sell_id));


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
                        String price = "Rs. "+c.getString(TAG_PRICE);
                        String description = "Description:  "+c.getString(TEG_DESC);

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



        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(
                            all_item_menu.this, productsList,
                            R.layout.item_value_list_menu, new String[]{TAG_PID, TAG_NAME, TAG_PRICE,TEG_DESC},
                            new int[]{R.id.seller_id_m, R.id.food_name, R.id.price,R.id.des});
                    // updating listview
                    lists.setAdapter(adapter);
                }
            });
            lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {

                    JSONObject c = null;
                    String rst   = null;
                    String names = null;
                    String price = null;
                    try {
                        c = products.getJSONObject(positions);
                        rst   = c.getString(TAG_PID);
                        names = c.getString(TAG_NAME);
                        price = c.getString(TAG_PRICE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent cart = new Intent(all_item_menu.this,select_quantity.class);
                    cart.putExtra("restid",rst);
                    cart.putExtra("names",names);
                    cart.putExtra("price",price);
                    startActivity(cart);

                }
            });


        }



    }}
