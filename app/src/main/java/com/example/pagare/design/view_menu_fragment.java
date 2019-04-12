package com.example.pagare.design;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.getIntent;
import static com.example.pagare.design.R.id.cart;

public class view_menu_fragment extends Fragment {

    View myView;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;

    String sell_id;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_menu_layout, container, false);


        lists  = (ListView) myView.findViewById(R.id.list_menus);
        productsList = new ArrayList<HashMap<String,String>>();
        new LoadAllProducts().execute();

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userid", Context.MODE_PRIVATE);
        sell_id=sharedPreferences.getString("vendorid","");

        return myView;

    }
    private class LoadAllProducts  extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            /*Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();*/
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

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    //Updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(
                            getActivity(), productsList,
                            R.layout.vendor_menu_list, new String[]{TAG_PID, TAG_NAME, TAG_PRICE,TEG_DESC},
                            new int[]{R.id.seller_id_m, R.id.food_name, R.id.price,R.id.des});
                    // updating listview
                    lists.setAdapter(adapter);

                }
            });
            Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();
        }
    }
}
