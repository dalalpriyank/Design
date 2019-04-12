package com.example.pagare.design;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class View_orders_vendor_yes_no extends AppCompatActivity implements View.OnClickListener {
    int ct=0;
    TextView coll,place;


    String College_id=null;



    String places="";


    Button ye_no;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    private static String url_all_products = "http://192.168.43.184/design/read_orders_y_n.php";

    ListView lists;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "college_id";
    private static final String TAG_NAME = "food_name";
    private static final String TAG_PRICE = "price";
    private static final String TEG_DESC = "quantity";
    private static final String TEG_PLACE = "place_to_delivered";


    // products JSONArray
    JSONArray products = null;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    private static String url_all_products_tmp = "http://192.168.43.184/design/insert_delivered.php";
    // JSON Node names
    private static final String TAG_SUCCESS_tmp = "success";
    private static final String TAG_PRODUCTS_tmp = "products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders_vendor_yes_no);
        Intent intent= getIntent();
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        College_id = intent.getStringExtra("ids");


        coll = (TextView) findViewById(R.id.Coll_id_yes_no);
        coll.setText("To ID : "+College_id);

        place = (TextView) findViewById(R.id.place_btn);


        ye_no = (Button) findViewById(R.id.yesnobtn);
        ye_no.setOnClickListener(this);

        lists = (ListView) findViewById(R.id.list_yn);

        productsList = new ArrayList<HashMap<String,String>>();
        new View_orders_vendor_yes_no.LoadAllProducts().execute();

    }

    @Override
    public void onClick(View v) {
        new View_orders_vendor_yes_no.loadall().execute();
        if(ct==0)
        {
            ye_no.setBackgroundColor(Color.GREEN);
            ct=1;
            ye_no.setText("Order Accepted");
            Intent in=new Intent(View_orders_vendor_yes_no.this,Vender_navigation.class);
            startActivity(in);
            finish();

        }
        else if(ct==1)
        {
            ye_no.setBackgroundColor(Color.RED);
            ct=0;
            ye_no.setText("Order cancleled");

        }
    }

    private  class loadall extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if(ct==0) {
                params.add(new BasicNameValuePair("delivered", "0"));
                params.add(new BasicNameValuePair("college_id", College_id));
            }
            else if(ct==1)
            {
                params.add(new BasicNameValuePair("delivered", "2"));
                params.add(new BasicNameValuePair("college_id", College_id));
            }


            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products_tmp, "GET", params);
            return null;
        }
    }

    private  class LoadAllProducts extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(View_orders_vendor_yes_no.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("college_id",College_id));

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

                    //JSONObject c2 = products.getJSONObject(0);
                    //places = c2.getString(TEG_PLACE);

                    for (int i = 0; i < products.length(); i++) {

                        JSONObject c = products.getJSONObject(i);
                        // Toast.makeText(AllProductsActivity.this, "here", Toast.LENGTH_LONG).show();

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String price = c.getString(TAG_PRICE);
                        String description = c.getString(TEG_DESC);
                        places= c.getString(TEG_PLACE);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_PRICE, price);
                        map.put(TEG_DESC, description);
                        map.put(TEG_PLACE, places);

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
            Toast.makeText(View_orders_vendor_yes_no.this, places, Toast.LENGTH_SHORT).show();
            Log.d("plss",places+"");
            View_orders_vendor_yes_no.this.runOnUiThread(new Runnable() {
                public void run() {
                    //Updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(
                            View_orders_vendor_yes_no.this, productsList,
                            R.layout.tmp_cart_selected, new String[]{TAG_NAME, TEG_DESC, TAG_PRICE},
                            new int[]{R.id.food_tmp, R.id.qty_tmp,R.id.total_tmp});
                    // updating listview
                    lists.setAdapter(adapter);

                    Log.d("plss",places+"");


                }

            });
            place.setText("Place : "+places);
        }
    }

}
