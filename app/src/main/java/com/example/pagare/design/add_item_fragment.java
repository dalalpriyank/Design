package com.example.pagare.design;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import static com.example.pagare.design.R.id.et_add_item;
import static com.example.pagare.design.R.id.radio;

/**
 * Created by Pagare on 24-Mar-17.
 */

public class add_item_fragment extends Fragment{

    EditText et_add,et_price,temp;
    MultiAutoCompleteTextView mtv_basicIngredients;
    Button btn_add;
    TextView wel;
    String str_et_add,str_et_price,str_mtv_basicIngredients;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView;
        myView = inflater.inflate(R.layout.add_item_layout,container,false);
        //temp= (EditText) myView.findViewById(R.id.u_id);
        et_add= (EditText) myView.findViewById(R.id.et_add_item);
        et_price= (EditText) myView.findViewById(R.id.et_add_price);
        mtv_basicIngredients= (MultiAutoCompleteTextView) myView.findViewById(R.id.mtv_basic_ingredients);
        btn_add= (Button) myView.findViewById(R.id.btn_add);
        wel= (TextView) myView.findViewById(R.id.wel);
/*
        et_add.setText("");
        et_price.setText("");
        mtv_basicIngredients.setText("");*/
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_et_add =et_add.getText().toString();
                str_et_price=et_price.getText().toString();
                str_mtv_basicIngredients=mtv_basicIngredients.getText().toString();

                if(str_et_add.length()==0){
                    et_add.setError("Please enter Item Name");
                }else if (str_et_price.length()==0){
                    et_price.setError("Enter Price of Item");
                }else if (str_mtv_basicIngredients.length()==0){
                    mtv_basicIngredients.setError("Required!");
                }else
                {
                    new add_item_fragment.LoadMenu().execute(str_et_add,str_et_price,str_mtv_basicIngredients);
                }

            }
        });

        return myView;

    }


    private class LoadMenu extends AsyncTask<String, Void,String>{
        String s=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s.equalsIgnoreCase("inserted\n")) {
                Toast.makeText(getActivity(),"Item Inserted!",Toast.LENGTH_LONG).show();
                /*SharedPreferences sharedPreferences=getActivity() .getSharedPreferences("userid",Context.MODE_PRIVATE);

                String id=sharedPreferences.getString("vendorid","");
                wel.setText(id);*/
                /*Toast.makeText(getActivity().getApplicationContext(),id,Toast.LENGTH_SHORT).show();*/
//                Toast.makeText(getActivity(),seller_id,Toast.LENGTH_LONG).show();
                /*Intent i=new Intent(getActivity(),Vender_navigation.class);
                startActivity(i);*/
                /*getActivity().finish();*/

            } else {
                Toast.makeText(getActivity(),"Item NOT Inserted!",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                /*HttpRequest httpRequest = new HttpRequest("http://192.168.0.100/design/menu_data.php");*/
                HttpRequest httpRequest = new HttpRequest("http://192.168.43.184/design/menu_data.php");

                /*HttpRequest httpRequest = new HttpRequest("http://file-manager.hostinger.in/6/insert.php");*/
                HashMap<String, String> hashMap = new HashMap<>();

//               hashMap.put("seller_id",trys);
                SharedPreferences sharedPreferences=getActivity() .getSharedPreferences("userid",Context.MODE_PRIVATE);
                String id=sharedPreferences.getString("vendorid","");

                hashMap.put("seller_id",id);

//                hashMap.put("seller_id","danny");
                hashMap.put("food_name", strings[0]);
                hashMap.put("price", strings[1]);
                hashMap.put("description", strings[2]);


                s = (httpRequest.prepare(HttpRequest.Method.POST).withData(hashMap).sendAndReadString());
                Log.d("data", s);


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("aa",e.toString());
            }

            return s;
        }


    }

}
//data analytics