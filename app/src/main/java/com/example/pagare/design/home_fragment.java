package com.example.pagare.design;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pagare on 18-Mar-17.
 */

public class home_fragment extends Fragment {


    View myView;
    private ProgressDialog pDialog;

    // Creating JSON Parser object


    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list
    /*private static String url_all_products = "http://192.168.0.100/design/read.php";*/
    private static String url_all_products = "http://192.168.43.184/design/read.php";
    ListView lists;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "seller_id";
    private static final String TAG_NAME = "restaurant_name";

    // products JSONArray
    JSONArray products = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.user_home,container,false);


        //remove down comm
        new LoadAllProducts().execute();

        lists  = (ListView) myView.findViewById(R.id.list);

        productsList = new ArrayList<HashMap<String,String>>();



        return myView;
    }

    private class LoadAllProducts  extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }


        @Override
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
            Log.d("All Products: ", json.toString());
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
                        //String last_name = c.getString(TAG_last_name);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);
                        //map.put(TAG_last_name, last_name);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } //Toast.makeText(AllProductsActivity.this, "problems ", Toast.LENGTH_LONG).show();
            }catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }







        protected void onPostExecute(String file_url){
            //pDialog.dismiss();
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    //Updating parsed JSON data into ListView
                    ListAdapter adapter = new SimpleAdapter(
                            getActivity(), productsList,
                            R.layout.list_item_layout, new String[] { TAG_PID,TAG_NAME},
                            new int[] { R.id.seller_id, R.id.restaurant_name });

                    // updating listview

                    lists.setAdapter(adapter);

                }
            });


        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {



                JSONObject c = null;
                String names = null;
                try {
                    c = products.getJSONObject(positions);
                    names = c.getString(TAG_PID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //down is perfect
                SharedPreferences sharedPrefer = getActivity().getSharedPreferences("vendorid", Context.MODE_PRIVATE);
                SharedPreferences.Editor edito=sharedPrefer.edit();
                edito.putString("seller_id",names);
                edito.commit();


                Intent ints = new Intent(getActivity(),all_item_menu.class);
                ints.putExtra("sid",names);

                startActivity(ints);
            }
        });


        }


    }
}


