package com.example.pagare.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class select_quantity extends AppCompatActivity implements View.OnClickListener {
    TextView fooditem,foodprice,quant,tprice,tedit;
    String fooditems;
    String foodprices;
    String tprices;
    String data;

    Button cartsub;
    FloatingActionButton plu,min;
    int qt=1,total=0,tp=0;
    String tmps="",tmp2="";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quantity);

        /*SharedPreferences sharedPreferences=select_quantity.this.getSharedPreferences("vendor_id", Context.MODE_PRIVATE);
        String vendor_id=sharedPreferences.getString("seller_id","");

        SharedPreferences sharedPrefer=select_quantity.this.getSharedPreferences("userid",Context.MODE_PRIVATE);
        String data=sharedPreferences.getString("custname","");*/


        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        fooditem =(TextView)findViewById(R.id.ditemname);
        foodprice = (TextView) findViewById(R.id.dprice);
        quant = (TextView) findViewById(R.id.quantity);
        tprice = (TextView) findViewById(R.id.totaledit);
        tedit = (TextView) findViewById(R.id.totaledit);


        plu = (FloatingActionButton) findViewById(R.id.plus);
        min = (FloatingActionButton) findViewById(R.id.minus);
        cartsub = (Button)findViewById(R.id.addcart);
        plu.setOnClickListener(this);
        min.setOnClickListener(this);

        SharedPreferences sharedPreferences=select_quantity.this.getSharedPreferences("clgid",Context.MODE_PRIVATE);
        data=sharedPreferences.getString("clg","");

        cartsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent datafororder = getIntent();

                tmps = datafororder.getStringExtra("custid");


                Intent cart = getIntent();
                tmp2 = cart.getStringExtra("restid");

               new LoadData().execute();




                finish();

                /*Intent inte=new Intent(getApplicationContext(),all_item_menu.class);
                startActivity(inte);
                */

            }
        });

        Intent cart = getIntent();

        fooditems = cart.getStringExtra("names");
        fooditem.setText(fooditems);

        foodprices = cart.getStringExtra("price");
        foodprice.setText("Rs."+foodprices);

        tprices = cart.getStringExtra("price");
        tedit.setText(tprices);

        total= Integer.parseInt(tprices);
        tp=total;




    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.plus)
        {
            String tmp,tmp1;
            int tmpt;
            qt=qt+1;
            tmp = String.valueOf(qt);
            quant.setText(tmp);

            total = tp*qt;
            tmp1 = String.valueOf(total);
            tprice.setText(tmp1);

        }
        else if(v.getId()== R.id.minus)
        {
            String tmp,tmp1 = null;
            int tmpt;

            if(qt>1)
            {
                qt=qt-1;
                tmp = String.valueOf(qt);
                quant.setText(tmp);


                total = tp*qt;
                tmp1 = String.valueOf(total);
                tprice.setText(tmp1);

            }
        }
        else
        {
            /*Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();*/
        }
    }



    class LoadData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String s = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(select_quantity.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                /*HttpRequest httpRequest = new HttpRequest("http://192.168.0.100/design/insert_rest.php");*/
                HttpRequest httpRequest = new HttpRequest("http://192.168.43.184/design/order_inserts.php");
                HashMap<String, String> hashMap = new HashMap<>();




              /*  SharedPreferences sharedPreferences=select_quantity.this.getSharedPreferences("vendor_id", Context.MODE_PRIVATE);
                String vendor_id=sharedPreferences.getString("seller_id","");

                SharedPreferences sharedPrefer=select_quantity.this.getSharedPreferences("userid",Context.MODE_PRIVATE);
                String data=sharedPrefer.getString("clg_id","");*/


                hashMap.put("cust_id",data);
                hashMap.put("seller_id",tmp2);
                hashMap.put("food_name",fooditems);
                hashMap.put("tprice", String.valueOf(foodprices.toString()));
                hashMap.put("quantity", String.valueOf(qt));

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
                Toast.makeText(getApplicationContext(),"Fill all the fields!"  , Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(),"Record Successfully inserted.", Toast.LENGTH_LONG).show();

        }


    }
}




