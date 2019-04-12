package com.example.pagare.design;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.pagare.design.MainActivity.MyPREFERENCES;

public class Navigation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    TextView welcome;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView tv;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__drawer);
        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);
        View wel=nv.getHeaderView(0);
        welcome = (TextView) wel.findViewById(R.id.welcome);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*String abc;
        abc="hello";
        welcome.setText(abc);*/


        SharedPreferences sharedPreferences=Navigation_Drawer.this.getSharedPreferences("userid",Context.MODE_PRIVATE);
        String data=sharedPreferences.getString("custname","");
        welcome.setText(data);


        FragmentManager fragmentManager= getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new home_fragment()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(), "Go to Navigation Drawer to Logout...", Toast.LENGTH_LONG).show();
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager= getFragmentManager();


        if (id == R.id.nav_home) {
            // Handle the camera action
            fragmentManager.beginTransaction().replace(R.id.content_frame,new home_fragment()).commit();
        } else if (id == R.id.nav_edit_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new edit_profile_fragment()).commit();

        } else if (id == R.id.nav_settings) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new settings_layout()).commit();
        } else if (id == R.id.nav_about_us) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new about_us_layout()).commit();
        } else if (id == R.id.nav_contact_us) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new contact_us_fragment()).commit();
        } else if (id == R.id.nav_logout) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new logout_fragment()).commit();
        }
        else {
            Toast.makeText(Navigation_Drawer.this,"chall",Toast.LENGTH_LONG).show();
            //            Intent intent1 = new Intent(Navigation_Drawer.this,)
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ice_berg(View view) {

        Toast.makeText(Navigation_Drawer.this,"Loading menu...",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,IceBerg.class));
    }

    public void shreeji_canteen(View view) {

        Toast.makeText(Navigation_Drawer.this,"Loading menu...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,shreeji_canteen.class));
    }

    public void danny(View view) {

        Toast.makeText(Navigation_Drawer.this,"Loading menu...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,Danny.class));
    }

    public void nescafe(View view) {

        Toast.makeText(Navigation_Drawer.this,"Loading menu...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,Nescafe.class));
    }

    public void krishna_chat(View view) {

        Toast.makeText(Navigation_Drawer.this,"Loading menu...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,Krishna_chat.class));
    }

    public void yes(View view) {

        finish();
        Toast.makeText(Navigation_Drawer.this,"Logout Successful...",Toast.LENGTH_SHORT).show();

    }

    public void no(View view) {

        startActivity(new Intent(this,Navigation_Drawer.class));
        Toast.makeText(Navigation_Drawer.this,"Redirecting to Home...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(Navigation_Drawer.this,"well do it now ",Toast.LENGTH_LONG).show();

    }


    private class open extends AsyncTask<String , Void,String >{

        @Override
        protected String doInBackground(String... strings) {

            String s=null;
            String sview=null;


            try {
                /*HttpRequest httpRequest=new HttpRequest("http://192.168.0.100/design/rest_data.php");*/

                HttpRequest httpRequest=new HttpRequest("http://192.168.43.184/design/rest_data.php");

                HashMap<String , String >hashMap=new HashMap<>();
                hashMap.put("sellerid",strings[0]);

                s=httpRequest.prepare(HttpRequest.Method.POST).withData(hashMap).sendAndReadString();
                Log.d("Values..",s);

                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray("customers");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    sview=jsonObject1.getString("restaurant_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Data error",e.toString());
            }

            return sview;



        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            /*SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            data = prefs.getString("chintu","");
            Log.d("myr",data);
            if (wel != null){
                wel.setText(data);
            }*/


        }
    }

}
